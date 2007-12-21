#for i in NewGroup Login Logout Register ; do cat GotoAction.java  | sed s/Goto/Register/g > RegisterAction.java ; done
for i in $* ; do cat GotoAction.java  | sed s/Goto/$i/g > S{i}Action.java ; done
