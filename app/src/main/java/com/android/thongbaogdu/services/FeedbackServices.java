package com.android.thongbaogdu.services;

import com.android.thongbaogdu.dao.IFeedbackDao;
import com.android.thongbaogdu.daoimpl.FeedbackDaoImpl;
import com.android.thongbaogdu.data.model.Feedback;

public class FeedbackServices {
    private IFeedbackDao feedbackDao = new FeedbackDaoImpl();

    public void sendFeedback(Feedback feedback)
    {
        feedbackDao.sendFeedback(feedback);
    }
}
