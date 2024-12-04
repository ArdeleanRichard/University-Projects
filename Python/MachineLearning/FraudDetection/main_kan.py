from sklearn.preprocessing import StandardScaler

from utils.kan import KAN, KANet

import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.utils import class_weight

from utils.neural_net import get_dataloaders_from_dataframes, NeuralNet
from utils.validation import binary_classification_report

# Dataset from: https://www.kaggle.com/datasets/mlg-ulb/creditcardfraud?select=creditcard.csv
df = pd.read_csv("./data/creditcard.csv")
y = df.iloc[:, -1].to_numpy()
print(df.columns)
print(df.head())

# Data scaling
scaler = StandardScaler()
df['Amount'] = scaler.fit_transform(df[['Amount']])

train_df, test_df = train_test_split(df, test_size=0.2, stratify=y)
print(train_df.head())
print(test_df.head())
nr_features = train_df.to_numpy().shape[1] - 1

print("\n" * 3)
print(f"[*] KAN Net")


# hyperparameters
hyperparam = {
    "batch_size": 512,
    "shuffle": True,
    "lr": 1e-3,
    "weight_decay": 1e-4,
    "gamma": 0.8,
    "n_epochs": 25,
    "input_dim": len(df.columns) - 1, # -1 for class
    "hidden_dim": 50,
    "output_dim": 2,
    "class_weights": class_weight.compute_class_weight(class_weight='balanced', classes=np.unique(y), y=y)
}

train_dl, test_dl = get_dataloaders_from_dataframes(hyperparam, train_df, test_df)


model = KANet(hyperparam)
model.fit(train_dl)
y_pred = model.predict(test_dl)
y_test = test_df.iloc[:, -1].to_numpy()

performance_dict = binary_classification_report(y_test, y_pred)
print(performance_dict)
