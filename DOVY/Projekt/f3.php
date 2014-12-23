<html>
<head>
	<title>Nedodané zásilky a volné prostředky - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#datum_den").datepicker({
			dateFormat: "dd/mm/yy"
		});
	});
	</script>
	
</head>
<body>
	<div id="main-div">
	
		<div id="menu">
			<ul id="simple-dropdown-nav">
				<li style="min-width:170px;"><a href="index.php" class="selected-tab">Formuláře</a>
					<ul>
						<li><a href="f1.php">Zákazníci podle počtu přijatých zásilek</a></li>
						<li><a href="f2.php">Auta a jejich ujeté kilometry</a></li>
						<li><a href="f3.php" class="selected-tab">Nedodané zásilky a volné prostředky</a></li>
						<li><a href="f4.php">Řidiči a auta podle najetých kilometrů</a></li>
						<li><a href="f5.php">Neuskutečněné a odmítnuté dodávky</a></li>
					</ul>
				</li>
				<li><a href="zakaznici.php">Zákazníci</a></li>
				<li><a href="ridici.php">Řidiči</a></li>
				<li><a href="auta.php">Auta</a></li>
				<li><a href="zakazky.php">Zakázky</a></li>
				<li style="min-width:150px;"><a href="dodavky.php">Dodávky</a></li>
			</ul>
		</div>
	
		<div id="content" style="min-height:340px;">
			<h3>Nedodané zásilky a volné prostředky</h3>
			
			<div id="zadani">
				<ul><li>přehled nedodaných zásilek a volných řidičů a aut pro zvolené datum</li></ul>
			</div>
			
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Zvolte den</th></tr>
				<tr>
					<td style="width:120px;">Den</td>
					<td><input name="datum_den" type="text" id="datum_den" size="30"></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;">
						<input name="show" type="submit" id="show" value="Vypsat">
					</td>
				</tr>
			</table>
			</form>
			<br>
			
			<h3><?php 
				if (!empty($_POST['datum_den'])) {
				echo $_POST['datum_den'];
			} else {
				echo "Nedodané zásilky";
			}
			
			 ?></h3>
			<table style="float:left; width:250px; margin-right:7px;">
				<tr>
					<th style="">id nevyřízených zásilek</th>
				</tr>
			<?php			
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}
					
					$sql = "SELECT id_zakazky FROM zakazka WHERE status = 'nevyřízeno'";
				
					mysql_query("SET CHARACTER SET utf8");
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
	
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_zakazky']}</td></tr>";
					} 
				
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
				
					mysql_close($conn);
				
			?>
			</table>
			<table style="float:left; width:250px; margin-right:7px;">
				<tr>
					<th style="">id volných řidičů</th>
				</tr>
			<?php		
				if(isset($_POST['show'])) {		
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}

					$datum_den = $_POST['datum_den'];
					
					list($den, $mesic, $rok) = explode("/", $datum_den);
					$datum_den = $rok."-".$mesic."-".$den;
		
					$sql = "SELECT id_ridice FROM ridic WHERE id_ridice NOT IN (SELECT id_ridice FROM dodavka WHERE datum = '$datum_den')";
				
					mysql_query("SET CHARACTER SET utf8");
				
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
	
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_ridice']}</td></tr>";
					} 
				
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
				
					mysql_close($conn);
				}				
			?>
			</table>
			<table style="float:left; width:250px;">
				<tr>
					<th style="">id volných aut</th>
				</tr>
			<?php		
				if(isset($_POST['show'])) {		
					$dbhost = 'localhost';
					$dbuser = 'dovyadmin';
					$dbpass = 'zasilkovasluzba';
					$conn = mysql_connect($dbhost, $dbuser, $dbpass);
					if(! $conn ) {
						die('Could not connect: ' . mysql_error());
					}

					$datum_den = $_POST['datum_den'];
					
					list($den, $mesic, $rok) = explode("/", $datum_den);
					$datum_den = $rok."-".$mesic."-".$den;
					
					$sql = "SELECT id_auta FROM auto WHERE id_auta NOT IN (SELECT id_auta FROM dodavka WHERE datum = '$datum_den')";
				
					mysql_query("SET CHARACTER SET utf8");
				
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
	
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_auta']}</td></tr>";
					} 
				
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
				
					mysql_close($conn);
				}				
			?>
			</table>
			<br>
			
		</div>
		
	</div>
</body>
</html>