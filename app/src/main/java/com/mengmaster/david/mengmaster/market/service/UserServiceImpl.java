package com.mengmaster.david.mengmaster.market.service;

import com.mengmaster.david.mengmaster.market.activity.LoginActivity;

/**
 * Created by dell on 2016/11/22.
 */
public class UserServiceImpl implements UserService {
    public void userLogin(String loginName, String loginPassword)
            throws Exception {


        Thread.sleep(3000);


		/*
		 * 模拟空指针异常
		 */
        // String str = null;
        // if(str.equals("david")){
        //
        // }

		/**
		 * 本地判断
		 */
         if(loginName.equals("david") && loginPassword.equals("123456")){

         }
         else{//业务错误
            throw new UserServiceRulesException(LoginActivity.MSG_LOGIN_FAILED);
         }
    }
}
