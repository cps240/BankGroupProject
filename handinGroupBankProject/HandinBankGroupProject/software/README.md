*ATM Machine*

* To run this, you must include the json requirement held in lib in the classpath.
* To use this, you must have a storage folder that is in the same folder as the bin folder (or the exe file if you wrap it as such). The folder must have the following:
	- users.json
		- this must have a json object in it with the following contents at least:


			{
				"Customers": [
				],
				"nextUserId": "1"
			}


	- accountRelationships.json
		- This must have a json object in it with the following contents at least:


			{
				"nextAccountId": "000000000000001"
			}