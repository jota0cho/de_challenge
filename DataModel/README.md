###The top 10 best games for each console/company.

```
WITH
con AS (  SELECT *
from "#PROJECTID.challenge.consoles")
SELECT
result.metascore as metascore,
result.name as name,
result.console as console,
result.userscore as userscore,
result.date as date,
con.company as company
from "#PROJECTID.challenge.result" result
LEFT JOIN con on con.console = result.console
order by metascore desc
limit 10
```


###The worst 10 games for each console/company.

```
WITH
con AS (  SELECT *
from `#PROJECTID.challenge.consoles`)
SELECT
result.metascore as metascore,
result.name as name,
result.console as console,
result.userscore as userscore,
result.date as date,
con.company as company
from `#PROJECTID.challenge.result` result
LEFT JOIN con on con.console = result.console
order by metascore asc
limit 10
```