from torch import nn
from torch import optim

from sklearn.metrics import accuracy_score

from tqdm import tqdm
import numpy as np
import torch
from torch.utils.data import DataLoader, Dataset


class MyDataset(Dataset):
    def __init__(self, x, y):
        self.x = x.values
        self.y = y.values

    def __len__(self):
        return len(self.x)

    def __getitem__(self, index):
        x = torch.tensor(self.x[index], dtype=torch.float32)
        y = torch.tensor(self.y[index], dtype=torch.long)
        return x, y


def get_dataloaders_from_dataframes(hyperparam, train_df, test_df):
    train_ds = MyDataset(
        x=train_df.iloc[:, :-1],
        y=train_df.iloc[:, -1]
    )
    train_dl = DataLoader(
        train_ds,
        batch_size=hyperparam["batch_size"],
        shuffle=hyperparam["shuffle"]
    )

    test_ds = MyDataset(
        x=test_df.iloc[:, :-1],
        y=test_df.iloc[:, -1]
    )
    test_dl = DataLoader(
        test_ds,
        batch_size=hyperparam["batch_size"]
    )

    return train_dl, test_dl


class Model(nn.Module):
    def __init__(self, input_dim, hidden_dim, output_dim, dropout):
        super().__init__()
        self.layers = nn.Sequential(
            nn.Dropout(dropout),
            nn.Linear(input_dim, hidden_dim),
            nn.ReLU(),
            nn.Dropout(dropout),
            nn.Linear(hidden_dim, output_dim),
        )

    def forward(self, x):
        return self.layers(x)

class NeuralNet():
    def __init__(self, hyperparam):
        self.hyperparam = hyperparam

        use_gpu = True
        self.device = torch.device('cuda' if use_gpu and torch.cuda.is_available() else 'cpu')

        # initialize the model, loss function, optimizer
        self.model = Model(
            self.hyperparam["input_dim"],
            self.hyperparam["hidden_dim"],
            self.hyperparam["output_dim"],
            self.hyperparam["dropout"]
        ).to(self.device)

        self.loss_func = nn.CrossEntropyLoss(weight=torch.tensor(self.hyperparam["class_weights"], dtype=torch.float).to(self.device))
        self.optimizer = optim.Adam(
            self.model.parameters(),
            lr=hyperparam["lr"],
            weight_decay=hyperparam["weight_decay"])



    def fit(self, train_dl):
        # lists used to store plotting data
        train_loss, train_acc = [], []

        # train the model
        for epoch in range(self.hyperparam["n_epochs"]):
            losses, acc = [], []

            # set model to training mode
            self.model.train()

            for X, y_true in tqdm(train_dl, desc=f'[train] epoch {epoch + 1} '):
                # clear gradients
                self.model.zero_grad()

                # send batch to right device
                X = X.to(self.device)
                y_true = y_true.to(self.device)

                # predict label scores
                y_pred = self.model(X)

                # compute loss
                loss = self.loss_func(y_pred, y_true)

                # compute accuracy
                gold = y_true.detach().cpu().numpy()
                pred = np.argmax(y_pred.detach().cpu().numpy(), axis=1)

                # accumulate for plotting
                losses.append(loss.detach().cpu().item())
                acc.append(accuracy_score(gold, pred))

                # backpropagate
                loss.backward()

                # optimize model parameters
                self.optimizer.step()

            # save epoch stats
            train_loss.append(np.mean(losses))
            train_acc.append(np.mean(acc))



    def predict(self, test_dl):
        # set model to evaluation mode
        self.model.eval()

        y_pred = []

        # disable gradient calculation
        with torch.no_grad():
            for X, _ in tqdm(test_dl):
                X = X.to(self.device)
                # predict one class per example
                y = torch.argmax(self.model(X), dim=1)
                # convert tensor to numpy array
                y_pred.append(y.cpu().numpy())

        # print results
        y_pred = np.concatenate(y_pred)

        return y_pred



