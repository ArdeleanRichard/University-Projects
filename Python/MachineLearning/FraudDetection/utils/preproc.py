import numpy as np

def standard_scaler(X):
    return (X - np.mean(X, axis=0)) / np.std(X, axis=0)

def train_test_split(X, y, train_size=0.8):
    indices = np.arange(X.shape[0])
    np.random.shuffle(indices)

    split_idx = int(X.shape[0] * train_size)

    X_train, X_test = X[indices[split_idx:]], X[indices[:split_idx]]
    y_train, y_test = y[indices[split_idx:]], y[indices[:split_idx]]

    return X_train, X_test, y_train, y_test
