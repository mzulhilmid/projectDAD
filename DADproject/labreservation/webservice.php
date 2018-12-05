<?php
	require_once("ConnectionDB.php");
	if (isset($_POST)) {
		
		$varFN = $_REQUEST["selectFn"];
		if ($varFN == "getAllLab") {
			$strQry = "	SELECT * FROM LAB ORDER BY labName";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute();
		}
		else if ($varFN == "getAllLabName") {
			$strQry = "	SELECT * FROM LAB WHERE labName = :labName;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labName' => $labName));
		}
		else if ($varFN == "deleteLab") {
			$labID = $_REQUEST["labID"];
			$strQry = "DELETE FROM lab
						WHERE labID=:labID";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labID' => $labID));
		}
		else if ($varFN == "searchLabByAbbrev") {
			$labAbbrev = $_REQUEST["labAbbrev"];
			$strQry = "	SELECT labID, labName, labAbbrev, labLocation, staffID FROM lab WHERE labAbbrev = :labAbbrev;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labAbbrev' => $labAbbrev));
		}
		else if ($varFN == "searchLabByName") {
			$labName = $_REQUEST["labName"];
			$strQry = "	SELECT labID, labName, labAbbrev, labLocation, staffID, labType FROM lab WHERE labName = :labName;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labName' => $labName));
		}
		else if ($varFN == "searchLabByStaffID") {
			$strQry = "	SELECT labID, labName, labAbbrev, labLocation, staffID, labType FROM lab WHERE staffID = :staffID;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('staffID' => $staffID));
		}
		else if ($varFN == "searchLabByType") {
			$labType = $_REQUEST["labType"];
			$strQry = "	SELECT labID, labName, labAbbrev, labLocation, staffID, labType FROM lab WHERE labType = :labType;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labType' => $labType));
		}
		else if ($varFN == "addLab") {
			$labName = $_REQUEST["labName"];
			$labAbbrev = $_REQUEST["labAbbrev"];
			$labLocation = $_REQUEST["labLocation"];
			$staffID = $_REQUEST["staffID"];
			$labType = $_REQUEST["labType"];
			$strQry = "INSERT INTO lab 
				(`labName`,
				`labAbbrev`,
				`labLocation`,
				`staffID`,
				`labType`)
				VALUES
				(:labName,:labAbbrev,:labLocation,:staffID,:labType)";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labName' => $labName,'labAbbrev' => $labAbbrev,'labLocation' => $labLocation,'staffID' => $staffID,'labType' => $labType));
		}
		else if ($varFN == "updateLab") {
			$labName = $_REQUEST["labName"];
			$labAbbrev = $_REQUEST["labAbbrev"];
			$labLocation = $_REQUEST["labLocation"];
			$staffID = $_REQUEST["staffID"];
			$labType = $_REQUEST["labType"];
			
			$strQry = "UPDATE lab
				SET
				`labName` = :labName,
				`labAbbrev` = :labAbbrev,
				`labLocation` = :labLocation,
				`staffID` = :staffID,
				`labType` = :labType
				WHERE `labID` = :labID;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labName' => $labName,'labAbbrev' => $labAbbrev,'labLocation' => $labLocation,'staffID' => $staffID,'labType' => $labType, 'labID' => $labID));			
		}
		
		else if ($varFN == "searchUser") {
			$username = $_REQUEST["username"];
			$password = $_REQUEST["password"];
			
			$strQry = "SELECT * FROM user
				WHERE `userID` = :userID;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute();			
		}
		
		else if ($varFN == "updateUser") {
			$username = $_REQUEST["username"];
			$password = $_REQUEST["password"];
			$position = $_REQUEST["position"];
			$phone = $_REQUEST["phone"];

			$strQry = "UPDATE user
				SET
				`username` = :username,
				`password` = :password,
				`position` = :position,
				`phone` = :phone
				WHERE `userID` = :userID;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('username' => $username,'password' => $password,'position' => $position,'phone' => $phone,'userID' => $userID));			
		}
		
		else if ($varFN == "deleteUser") {
			$username = $_REQUEST["username"];
			$strQry = "DELETE FROM user
						WHERE username=:username";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('username' => $username));
		}
		
		else if ($varFN == "addUser") {
			$username = $_REQUEST["username"];
			$password = $_REQUEST["password"];
			$position = $_REQUEST["position"];
			$phone = $_REQUEST["phone"];
			$strQry = "INSERT INTO user 
				(`username`,`password`,`position`,`phone`)
				VALUES
				(:username,:password,:position,:phone)";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('username' => $username,'password' => $password,'position' => $position,'phone' => $phone));
		}
		
		else if ($varFN == "searchByUser") {
			$username = $_REQUEST["username"];
			$strQry = "	SELECT userID, username, password,position, phone FROM user
			WHERE username = :username;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('username' => $username));
		}
		
		else if ($varFN == "searchByPosition") {
			$position = $_REQUEST["position"];
			$strQry = "	SELECT * FROM user 
			WHERE position = :position;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute();
		}
		
		else if ($varFN == "checkIfAvailable") {
			$strQry = "	SELECT * FROM reservation
						WHERE timeReservedStart = :timeReservedStart
						AND timeReservedEnd = :timeReservedEnd
						AND dateReserved = :dateReserved;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('timeReservedStart' => $timeReservedStart,'timeReservedEnd' => $timeReservedEnd,'dateReserved' => $dateReserved));
		}
		else if ($varFN == "loadReservation") {
			$labID = $_REQUEST["labID"];
			$strQry = "SELECT timeReservedStart,timeReservedEnd, dateReserved,reservedBy,reason, approvalStatus FROM reservation where labID=:labID";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('labID' => $labID));
		}
		else if ($varFN == "deleteReservation") {
			$reservationID = $_REQUEST["reservationID"];
			$strQry = "	DELETE FROM reservation WHERE reservationID = :reservationID;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('reservationID' => $reservationID));
		}
		
		else if ($varFN == "addNewReservation"){
			
			$dateReserved = $_REQUEST["dateReserved"];
			$timeReservedStart = $_REQUEST["timeReservedStart"];
			$timeReservedEnd = $_REQUEST["timeReservedEnd"];
			$reservedBy = $_REQUEST["reservedBy"];
			$reason = $_REQUEST["reason"];
			$labID = $_REQUEST["labID"];

			$strQry = "INSERT INTO reservation 
					(`dateReserved`, `timeReservedStart`, `timeReservedEnd`, `reservedBy`, `reason`, `labID`)
					VALUES (:dateReserved, :timeReservedStart, :timeReservedEnd, :reservedBy, :reason, :labID)";
			
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('dateReserved' => $dateReserved,'timeReservedStart' => $timeReservedStart,'timeReservedEnd' => $timeReservedEnd,'reservedBy' => $reservedBy,'reason' => $reason,'labID' => $labID));
		}
		
		else if ($varFN == "getReservedByUser") {
			$reservedBy = $_REQUEST["reservedBy"];
				
			$strQry = "	SELECT lab.labName AS Name, reservation.timeReservedStart AS Start, reservation.timeReservedEnd AS End, reservation.dateReserved AS Date, reservation.reason AS Reason, reservation.approvalStatus AS Status, reservation.reservationID AS rID, user.username AS username
						FROM reservation
						JOIN lab ON reservation.labID = lab.labID
						JOIN user on reservation.reservedBy = user.userID
						WHERE reservation.reservedBy = :reservedBy;";

			
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('reservedBy' => $reservedBy));
		}
		
		else if ($varFN == "getReservedAll") {
			$approvalStatus = $_REQUEST["approvalStatus"];
			
			$strQry = "	SELECT lab.labName, reservation.timeReservedStart, reservation.timeReservedEnd, reservation.dateReserved, user.username, reservation.reason,reservation.approvalStatus, reservation.reservationID FROM reservation 
			JOIN lab ON reservation.labID = lab.labID 
			JOIN user ON reservation.reservedBy = user.userID 
			WHERE reservation.approvalStatus = :approvalStatus";
			
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('approvalStatus' => $approvalStatus));
		}
		
		else if ($varFN == "getReservedByStaff") {
			$approvalStatus = $_REQUEST["approvalStatus"];
			$staffID = $_REQUEST["staffID"];
			
			$strQry = "	SELECT lab.labName AS Name, reservation.timeReservedStart AS Start, reservation.timeReservedEnd AS End, reservation.dateReserved AS Date, reservation.reason AS Reason, reservation.approvalStatus AS Status, reservation.reservationID AS rID, user.username AS username FROM reservation 
			JOIN lab ON reservation.labID = lab.labID 
			JOIN user ON reservation.reservedBy = user.userID 
			WHERE reservation.approvalStatus = :approvalStatus
			AND lab.staffID = :staffID;";
			
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('approvalStatus' => $approvalStatus,'staffID' => $staffID));
		}
		
		else if ($varFN == "changeStatus") {
			$reservationID = $_REQUEST["reservationID"];
			$approvalStatus = $_REQUEST["approvalStatus"];
			
			$strQry = "UPDATE `reservation`
						SET
						`approvalStatus` = :approvalStatus
						WHERE `reservationID` = :reservationID;";
			
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('reservationID' => $reservationID,
			'approvalStatus' => $approvalStatus));
		}
		
		$record=$stmt->fetchAll(PDO::FETCH_OBJ);
		echo json_encode($record);
	}
?>