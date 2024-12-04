import numpy as np
from tqdm import tqdm


class LogisticRegresser:
    def __init__(self, X_train, y_train):
        self.X_train = X_train
        self.y_train = y_train
        # initialize model (with 0s), as an array of weights w (size: nr features) and a bias term b
        self.w = np.zeros(X_train.shape[1])
        self.b = 0

    def fit(self, n_epochs=10):
        indices = np.arange(self.X_train.shape[0])
        for epoch in range(n_epochs):
            n_errors = 0

            # randomize the order in which training examples are seen in this epoch
            np.random.shuffle(indices)

            # traverse the training data
            for i in tqdm(indices, desc=f'epoch {epoch + 1}'):
                x, y_true = self.X_train[i], self.y_train[i]

                # perceptron decision
                score = x @ self.w + self.b
                y_pred = 1 if score > 0 else 0

                if y_true == y_pred:
                    continue

                # update the model if the prediction was incorrect
                elif y_true == 1 and y_pred == 0:
                    self.w = self.w + x
                    self.b = self.b + 1
                    n_errors += 1
                elif y_true == 0 and y_pred == 1:
                    self.w = self.w - x
                    self.b = self.b - 1
                    n_errors += 1
            if n_errors == 0:
                break

        return self.w, self.b

    def predict(self, X_test):
        y_pred = (X_test @ self.w + self.b) > 0
        return y_pred