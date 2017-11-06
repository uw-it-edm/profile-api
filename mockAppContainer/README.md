You can run a mock profile API easily using Docker.
The main difference with the "real" profile-api is that groups won't be checked against the logged user.
The container will return a static list of available apps


First, build the container :

 ```docker build -t profile-api-mock . ```
 
Then, run it, by exposing a port, and mounting a volume where the configs and the static configs list is ( see `profile-api-example` for an example of the directory structure )

```docker run --name profile-api -it --rm  -p 8080:80 -v ${PWD}/profile-api-example/:/usr/share/nginx/html/ profile-api-mock```

you should now be able to access 

```http :8080/profile/v1/app/my-app```

for the list of available config for an app 

and 

```http :8080/profile/v1/app/my-app/demo```

for the demo config 