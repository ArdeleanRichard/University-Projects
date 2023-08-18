import cv2
import numpy as np
import matplotlib.pyplot as plt
import keras

from constants import IMAGE_SIZE


def detect():
    # Load and preprocess the image
    image_path = "./dataset/images/IMG_3770.JPG"
    mask_path = "./dataset/combined_masks/IMG_3770_combined.png"
    image = cv2.imread(image_path)
    image = cv2.resize(image, (IMAGE_SIZE, IMAGE_SIZE))  # Resize to match model input shape
    image = np.expand_dims(image, axis=0)  # Add batch dimension

    # Load the trained model
    model = keras.models.load_model("models/mobile_large_1024_only_cracks.h5")

    # Generate predictions
    predictions = model.predict(image)
    # predictions = (predictions > 0.5).astype(np.uint8)  # Threshold the predictions
    predictions = np.argmax(predictions, axis=-1).astype(np.uint8)  # Threshold the predictions

    mask = cv2.imread(mask_path, cv2.IMREAD_GRAYSCALE)

    # Visualize the original image and the predicted mask
    plt.subplot(1, 3, 1)
    plt.imshow(image[0])
    plt.title("Original Image")

    plt.subplot(1, 3, 2)
    plt.imshow(mask, cmap="gray")
    plt.title("True mask")

    plt.subplot(1, 3, 3)
    plt.imshow(predictions[0], cmap="gray")
    plt.title("Detected Brick Walls")

    plt.show()


detect()
