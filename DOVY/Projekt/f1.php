<html>
<head>
	<title>Zákazníci podle počtu přijatých zásilek - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
</head>
<body>
	<div id="main-div">
	
		<div id="menu">
			<ul id="simple-dropdown-nav">
				<li style="min-width:170px;"><a href="index.php" class="selected-tab">Formuláře</a>
					<ul>
						<li><a href="f1.php" class="selected-tab">Zákazníci podle počtu přijatých zásilek</a></li>
						<li><a href="f2.php">Auta a jejich ujeté kilometry</a></li>
						<li><a href="f3.php">Nedodané zásilky a volné prostředky</a></li>
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
		<?php
			if (!empty($_POST['input_rok'])) {
				$rok = $_POST['input_rok'];
			} else {
				$rok = 2014;
			}
		?>
	
		<div id="content">
			<h3>Zákazníci podle počtu přijatých zásilek</h3>
			
			<div id="zadani">
				<ul><li>přehled zákazníků podle celkového počtu přijatých zásilek za zvolený rok</li></ul>
			</div>
			
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Zvolte rok</th></tr>
				<tr>
					<td style="width:120px;">Rok</td>
					<td>
						<select name="input_rok" size="1" style="width:106px;font-size:20px;" id="input_rok">
							<option value="2015" <?php if($rok==2015) echo 'selected="selected"'; ?>>2015</option>
							<option value="2014" <?php if($rok==2014) echo 'selected="selected"'; ?>>2014</option>
							<option value="2013" <?php if($rok==2013) echo 'selected="selected"'; ?>>2013</option>
							<option value="2012" <?php if($rok==2012) echo 'selected="selected"'; ?>>2012</option>
							<option value="2011" <?php if($rok==2011) echo 'selected="selected"'; ?>>2011</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center;">
						<input name="show" type="submit" id="show" value="Vypsat">
					</td>
				</tr>
			</table>
			</form>
			<br>
			
			<h3><?php echo $rok; ?></h3>
			<table>
				<tr>
					<th style="width:120px;">id_zakaznika</th>
					<th>jméno</th>
					<th>adresa</th>
					<th style="width:70px;">počet</th>
				</tr>
			<?php
				$dbhost = 'localhost';
				$dbuser = 'dovyadmin';
				$dbpass = 'zasilkovasluzba';
				$conn = mysql_connect($dbhost, $dbuser, $dbpass);
				if(! $conn ) {
					die('Could not connect: ' . mysql_error());
				}

				$rok = $_POST['input_rok'];
				if ($rok == "") $rok = 2014;
				settype($rok, "integer");
		
				$sql = "SELECT * FROM (SELECT id_zakaznika, jmeno, adresa, pocet ".
					"FROM zakaznik NATURAL JOIN (SELECT id_zakaznika, COUNT(*) AS pocet ".
					"FROM (SELECT id_zakaznika FROM dodavka NATURAL JOIN zakazka ".
					"WHERE zakazka.status = 'vyřízeno' AND EXTRACT(year FROM datum) = $rok) AS temp ".
					"GROUP BY id_zakaznika) AS counts UNION ".
					"SELECT id_zakaznika, jmeno, adresa, 0 AS pocet FROM ".
					"zakaznik WHERE zakaznik.id_zakaznika NOT IN (SELECT id_zakaznika ".
					"FROM (SELECT id_zakaznika FROM dodavka NATURAL JOIN zakazka ".
					"WHERE zakazka.status = 'vyřízeno' AND EXTRACT(year FROM datum) = $rok) AS temp ".
					"GROUP BY id_zakaznika)) as vysledek ORDER BY pocet DESC, id_zakaznika";
				
				mysql_query("SET CHARACTER SET utf8");
				
				mysql_select_db('dovydata');
				$retval = mysql_query( $sql, $conn );
	
				while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
					echo "<tr><td>{$row['id_zakaznika']}</td>".
						"<td>{$row['jmeno']}</td>".
						"<td>{$row['adresa']}</td>".
						"<td style=\"text-align:center;color:#d00;\">{$row['pocet']}</td></tr>";
				} 
				
				if(! $retval ) {
					die('Could not get data: ' . mysql_error());
				}
				
				mysql_close($conn);
				
			?>
			</table>
			<br>
			
		</div>
		
	</div>
</body>
</html>