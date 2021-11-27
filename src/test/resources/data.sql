INSERT INTO `bank_information` (`ID`,`BANK`,`BIC`,`DISPLAY`,`IBAN`  )
VALUES (1,'SG','BIC','DISPLAY','43222'), (2,'BNP','BIC','DISPLAY','87764');

INSERT INTO `account` (`id`, `first_name`, `last_name`, `account_number`, `bank_information_id`, `amount`)
VALUES (1,  'MAHDI',	'HADDAR',	1111,		1,	10000.0), (2,  'CYRINE',	'GHORBEL',	2222	,		2,	30000.0);
