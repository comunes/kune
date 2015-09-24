select count(*) signInCount, DATE_FORMAT(FROM_UNIXTIME(signInDate * 0.001), "%Y-%m-%d") d FROM user_signin_log l, kusers u WHERE l.user_id=u.id GROUP BY d ORDER BY d DESC;
#select count(*) signInCount, DATE_FORMAT(FROM_UNIXTIME(signInDate * 0.001), "%Y-%m-%d") d FROM user_signin_log l, kusers u WHERE l.user_id=u.id GROUP BY d ORDER BY signInCount DESC;
