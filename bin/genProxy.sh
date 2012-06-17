#echo buider.exclude\(\"$2\"\)\;
#exit
cat << EOF
<servlet>
<servlet-name>Wiab$1</servlet-name>
<servlet-class>org.eclipse.jetty.servlets.ProxyServlet\$Transparent</servlet-class>
<init-param>
<param-name>ProxyTo</param-name><param-value>http://127.0.0.1:9898/</param-value>
</init-param>
<init-param>
<param-name>Prefix</param-name><param-value>/</param-value>
</init-param>
</servlet>  
<servlet-mapping>
<servlet-name>Wiab$1</servlet-name>
<url-pattern>$2</url-pattern>
</servlet-mapping>
EOF
