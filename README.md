# graphql-github-client
An android app that consumes the Github GraphQL api with apollo android client.

#
## Features
- **Users**
  - Search all users 
  - Filter users using a query string
- **Repositories**
  - Get all repositories of an specific user (no forked)
- **Pagination**
  - Load N entries of users and repositories using onScroll Listener 
  #
## Steps to Generate the access TOKEN

In order to create a personal access token and be able to use the Github GraphQL API in this project follow the next steps:

- First of all you need to verify your email address.
- Click your profile photo (in the upper-right corner of of the Github page), then click Settings.
- Click Personal access tokens in the left sidebar.
- Click Generate new token.
- Fill a descriptive name.
- Then select the needed scopes and permissions of the token, as this application only needs read the some information of users and public repositories   I only selected two scopes (User and repositories) but you can select the scopes you need.
- Finally click Generate token and save it because it won’t be displayed again the future.
#

## Add the Github TOKEN created before in the project
```
Github's api key is stored in api_key.xml file in res/values folder, It is not in this repository.
So please create it, like below

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="github_graphql__token">YOUR_API_KEY_HERE</string>
</resources>

This file should be in the location: app/src/main/res/values/api_key.xml
```

#
## License

>Copyright © 2018 Anthony Alfredo Ccapira Avendaño

>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at

>   http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.