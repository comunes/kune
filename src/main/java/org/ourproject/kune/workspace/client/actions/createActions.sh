#for i in NewGroup Login Logout Register ; do cat GotoAction.java  | sed s/Goto/Register/g > RegisterAction.java ; done
#for i in $* ; do cat GotoAction.java  | sed s/Goto/$i/g > ${i}Action.java ; rm ${i}Action.java ; done
for i in $* ; do cat GotoAction.java  | sed s/Goto/Content$i/g > Content${i}Action.java ; done
