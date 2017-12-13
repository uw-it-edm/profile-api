FROM nginx

RUN rm /etc/nginx/conf.d/default.conf

COPY 00_profile.conf /etc/nginx/conf.d/00_profile.conf
COPY nginx.conf /etc/nginx/nginx.conf