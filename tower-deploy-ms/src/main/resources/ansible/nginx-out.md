# nginx-out 使用說明

## 變量

* ms 對應的微服務的名稱，NGINX upstream的名稱也是這個
* upstream_dir 對應的nginx存放upstream的地方，這裡要求將nginx的upstream獨立放置
* upstreams 想要控制的實例