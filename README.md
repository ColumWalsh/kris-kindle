# Kris Kindle 
Organise a kris kindle (Secret Santa) gift exchange through sms. Needs a twilio account, sign up [here](http://www.twilio.com/referral/nhMAbbi).

## Properties
These properties are needed in an application.yaml file
```
twilio:
   number: 
   account:
      sid: 
   auth:
      token: 
```
## Running It
Sample curl command for a dry run
```
curl -X POST -H "Content-Type: application/json"     -d '[{"name":"rudolf","number":"+353123"},{"name":"santa","number":"+353456"},{"name":"mr hanky","number":"+1123", "partner":{"name":"mrs hanky"}},{"name":"mrs hanky","number":"+1123", "partner":{"name":"mr hanky"}}]'    "http://localhost.localdomain:8080/KrisKindle?dryRun=true&limit=50"
```
Sample curl command for a real execution
```
curl -X POST -H "Content-Type: application/json"     -d '[{"name":"rudolf","number":"+353123"},{"name":"santa","number":"+353456"}]'    "http://localhost.localdomain:8080/KrisKindle?limit=50"

```
