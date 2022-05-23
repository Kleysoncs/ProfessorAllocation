package com.example.professorallocation.data.service;

public interface RepositoryCallBack<T> {
    void onResponse(T response);

    void onFailure(Throwable t);
}
