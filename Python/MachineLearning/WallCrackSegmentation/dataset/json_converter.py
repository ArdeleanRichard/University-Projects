import cv2
import json
import numpy as np
import os

json_file = "annotations.json"

mask_folder = "masks_backup/"

with open(json_file) as file:
    json_data = json.load(file)

for img_key, img_data in json_data.items():
    if img_key != "___sa_version___":
        instances = img_data["instances"]

        image = cv2.imread('images/' + img_key)

        # Create a blank mask
        mask = np.zeros_like(image[:, :, 0])

        # Iterate over instances and draw masks_backup
        for instance in instances:
            # Extract polygon points
            points = np.array(instance["points"], np.int32).reshape(-1, 2)

            # Draw filled polygon on the mask
            cv2.fillPoly(mask, [points], 255)

        # Save the mask as a black and white image in the specified folder
        mask_file = os.path.join(mask_folder, f"{img_key.split('.')[0]}_mask.png")
        cv2.imwrite(mask_file, mask)
