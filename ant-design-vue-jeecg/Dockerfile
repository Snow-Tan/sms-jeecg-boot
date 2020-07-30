FROM nginx:perl
MAINTAINER jeecgos@163.com
VOLUME /tmp
ENV LANG en_US.UTF-8
RUN echo "server {
                      listen       80;
                      location ^~ /jeecg-boot {
                      proxy_pass              http://sms-h5-backend.default:30003;
                      proxy_set_header        Host jeecg-boot-system;
                      proxy_set_header        X-Real-IP \$remote_addr;
                      proxy_set_header        X-Forwarded-For \$proxy_add_x_forwarded_for;
                  }
                      location  /sms-h5-backend {
                      proxy_pass              http://sms-h5-backend.default:30003;
                      proxy_set_header        Host sms-h5-backend;
                      proxy_set_header        X-Real-IP \$remote_addr;
                      proxy_set_header        X-Forwarded-For \$proxy_add_x_forwarded_for;
                  }
                      location  =/sms-h5-backend2 {
                      proxy_pass              http://sms-h5-backend.default:30003;
                      proxy_set_header        Host sms-h5-backend;
                      proxy_set_header        X-Real-IP \$remote_addr;
                      proxy_set_header        X-Forwarded-For \$proxy_add_x_forwarded_for;
                  }
                      location  =/sms-h5-backend3 {
                      proxy_pass              http://sms-h5-backend.default:30003;

                  }
                      location  /sms-h5-backend4 {
                      proxy_pass              http://sms-h5-backend.default:30003;

                  }
                  #解决Router(mode: 'history')模式下，刷新路由地址不能找到页面的问题 \
                  location / {
                     root   /var/www/html/;
                      index  index.html index.htm;
                      if (!-e \$request_filename) {
                          rewrite ^(.*)\$ /index.html?s=\$1 last;
                          break;
                      }
                  }
                  access_log  /var/log/nginx/access.log ;
              } " > /etc/nginx/conf.d/default.conf 
RUN  mkdir  -p  /var/www
RUN  mkdir -p /var/www/html

ADD dist/ /var/www/html/
EXPOSE 80
EXPOSE 443