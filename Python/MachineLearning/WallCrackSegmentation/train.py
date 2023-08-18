import math
import os
import numpy as np
import cv2
from tensorflow.python.keras.callbacks import LearningRateScheduler

from constants import IMAGE_SIZE, EPOCHS, LEARNING_RATE, STEPS_PER_EPOCH, VALIDATION_STEPS
from mask_unifier import mask_unifier
from model_mobile import mobile_model
import tensorflow as tf
from IPython.display import clear_output

from model_predict import display_data_pred, create_mask
from data_generator import model_data_generator
from model_train_plot import plot_loss_n_acc, plot_history


mask_unifier()
trainGenerator, valGenerator, (sample_image, sample_mask, sample_weights) = model_data_generator()


print(sample_image.shape)
print(sample_mask.shape)


model = mobile_model()
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=LEARNING_RATE),
              loss=tf.keras.losses.SparseCategoricalCrossentropy(), #from_logits=True
              metrics=['accuracy'])

for layer in model.layers:
    print(layer.output_shape)



retain_loss = []
retain_acc = []
retain_val_loss = []
retain_val_acc = []


class DisplayCallback(tf.keras.callbacks.Callback):
    def on_epoch_end(self, epoch, logs=None):
        retain_loss.append(logs['loss'])
        retain_acc.append(logs['accuracy'])
        retain_val_loss.append(logs['val_loss'])
        retain_val_acc.append(logs['val_accuracy'])

        if (epoch+1) % 5 == 0:
            clear_output(wait=True)
            display_data_pred([sample_image, sample_mask, create_mask(model.predict(sample_image[tf.newaxis, ...]))])
            # print('\nSample Prediction after epoch {}\n'.format(epoch+1))
            # print(f"The average loss for epoch {epoch} is {logs['loss']:7.7f} and mean absolute error is {logs['accuracy']:7.7f}.")

            plot_loss_n_acc(range(epoch+1), retain_loss, retain_acc, retain_val_loss, retain_val_acc)


def lr_step_decay(epoch):
    drop_rate = 0.25
    epochs_drop = EPOCHS / 10
    return LEARNING_RATE * math.pow(drop_rate, math.floor(epoch / epochs_drop))



model_history = model.fit(trainGenerator,
                          epochs=EPOCHS,
                          steps_per_epoch=STEPS_PER_EPOCH,
                          validation_steps=VALIDATION_STEPS,
                          validation_data=valGenerator,
                          callbacks=[DisplayCallback(),
                                     LearningRateScheduler(lr_step_decay, verbose=1),
                                     # EarlyStopping(monitor='val_loss', patience=2),
                                     ]
                          )

plot_history(model_history)

model.save('models/mobile_large_1024_only_cracks.h5')