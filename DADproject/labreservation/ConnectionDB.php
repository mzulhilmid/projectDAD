<?php
	$hostAddr = "localhost";
	$dbName = "labreservation";
	$dbUser = "root";
	$dbPwd = "";
	$dbPort = 3306;
	$dbPDO = new PDO("mysql:host=$hostAddr;dbname=$dbName",$dbUser,$dbPwd);
	#$dbPDO->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_WARNING);
?>