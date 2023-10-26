### Linux 常用服务类相关命令

**常用基本命令 - 进程类**

> Service ( Centos6) 

```text
service服务名start
service服务名stop
service服务名restart
service服务名reload
service服务名status

#查看服务的方法 /etc/init.d/ 服务名
#通过 chkconfig 命令设置自启动
#查看服务 chkconfig -list l grepXXX

chkconfig -level 5 服务名on

```

> Systemctl ( Centos7 )

```textmate
systemctl start 服务名(xxx.service
systemct restart 服务名(xxxx.service)
systemctl stop 服务名(xxxx.service)
systemctl reload 服务名(xxxx.service)
systemctl status 服务名(xxxx.service)

#查看服务的方法 /usr/lib/systemd/system
#查看服务的命令

systemctl list-unit-files
systemctl --type service

#通过systemctl命令设置自启动

自启动systemctl enable service_ _name
不自启动systemctl disable service_ name

```
