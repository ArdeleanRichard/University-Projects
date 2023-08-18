
# Set the input and mask directories
IMAGES_DIR = './dataset/images/'
OG_MASKS_DIR = './dataset/black_background/'
MASKS_DIR = './dataset/combined_masks/'

IMAGE_SIZE = 1024
# IMAGE_SIZE = 224
EPOCHS = 50
BATCH_SIZE = 4
# CLASSES = 7
# CLASSES_WEIGHTS = [3.0, 10.0, 1.0, 1.0, 1.0, 1.0, 1.0]
# # CLASSES_WEIGHTS = [3.0, 30.0, 1.0, 1.0, 1.0, 1.0, 1.0]

CLASSES = 2
CLASS_WEIGHTS = [1.0, 30.0]

CLASS_WEIGHTS_SUM = sum(CLASS_WEIGHTS)

TRAIN_DATA_SIZE = 4800
VAL_DATA_SIZE = 1200
VAL_SUBSPLITS = 10
VALIDATION_STEPS = VAL_DATA_SIZE // BATCH_SIZE // VAL_SUBSPLITS
STEPS_PER_EPOCH = TRAIN_DATA_SIZE // BATCH_SIZE // 2
LEARNING_RATE = 0.01


CLASS_MAPPER = {
    "z": 1,
    "E": 2,
    "F": 3,
    "R": 4,
    "V": 5,
    "W": 6,
    "C": 7
}