package com.android.thongbaogdu.dao;

import com.android.thongbaogdu.data.model.Feedback;

public interface IFeedbackDao {
    void sendFeedback(Feedback feedback);
}
