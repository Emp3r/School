<html>
<head>
	<title>Řidiči a auta podle najetých kilometrů - Přepravní balíková služba</title>
	<meta charset="UTF-8">
	<meta name="author" content="Empe">
	<link rel="stylesheet" href="styles.css">
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	
	
</head>
<body>
	<div id="main-div">
	
		<div id="menu">
			<ul id="simple-dropdown-nav">
				<li style="min-width:170px;"><a href="index.php" class="selected-tab">Formuláře</a>
					<ul>
						<li><a href="f1.php">Zákazníci podle počtu přijatých zásilek</a></li>
						<li><a href="f2.php">Auta a jejich ujeté kilometry</a></li>
						<li><a href="f3.php">Nedodané zásilky a volné prostředky</a></li>
						<li><a href="f4.php" class="selected-tab">Řidiči a auta podle najetých kilometrů</a></li>
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
			<h3>Řidiči a auta podle najetých kilometrů</h3>
			
			<div id="zadani">
				<ul><li>přehled řidičů a aut podle počtu najetých km ve zvoleném měsíci</li></ul>
			</div>
			
			<form method="post" action="<?php $_PHP_SELF ?>" >
			<table>
				<tr><th colspan="2">Zvolte měsíc</th></tr>
				<tr>
					<td style="width:120px;">Měsíc</td>
					<td><?php
							if (!empty($_POST['datum_mesic'])) { $mesic = $_POST['datum_mesic'];
							} else { $mesic = 12; } ?>
						<select name="datum_mesic" size="1" style="width:106px;font-size:20px;" id="datum_mesic">
							<?php
							for ($i = 12; $i > 0; $i--) {
								echo "<option value=\"".$i."\" ";
								if ($mesic == $i) echo 'selected="selected"';
								echo ">".$i."</option>";
							}
							?>
						</select>
					</td>
				</tr>
				<tr>
					<td>Rok</td>
					<td><?php
							if (!empty($_POST['datum_rok'])) { $rok = $_POST['datum_rok'];
							} else { $rok = 2014; } ?>
						<select name="datum_rok" size="1" style="width:106px;font-size:20px;" id="datum_rok">
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
			
			<h3><?php 
				if (!empty($_POST['datum_mesic'])) { 
					echo "měsíc ".$_POST['datum_mesic'].", "; 
					echo "rok ".$_POST['datum_rok']; 
				}
			?></h3>
			<table style="float:left; width:49%;">
				<tr>
					<th style="">id řidiče</th>
					<th>jméno</th>
					<th style="">najeto</th>
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

					$datum_mesic = $_POST['datum_mesic'];
					$datum_rok = $_POST['datum_rok'];
					
					$sql = "SELECT temp.*, jmeno FROM ridic NATURAL JOIN ".
						"(SELECT id_ridice, SUM(najeto_km) AS najeto FROM dodavka ".
						"WHERE MONTH(datum) = $datum_mesic AND YEAR(datum) = $datum_rok ".
						"GROUP BY id_ridice) AS temp ORDER BY najeto DESC";
				
					mysql_query("SET CHARACTER SET utf8");
				
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
	
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_ridice']}</td><td>{$row['jmeno']}</td>".
							"<td style=\"color:#d00;\">{$row['najeto']} km</td></tr>";
					} 
				
					if(! $retval ) {
						die('Could not get data: ' . mysql_error());
					}
				
					mysql_close($conn);
				}				
			?>
			</table>
			<table style="float:right; width:49%;">
				<tr>
					<th style="">id auta</th>
					<th style="">SPZ</th>
					<th style="">najeto</th>
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

					$datum_mesic = $_POST['datum_mesic'];
					$datum_rok = $_POST['datum_rok'];
					
					$sql = "SELECT temp.*, spz FROM auto NATURAL JOIN ".
						"(SELECT id_auta, SUM(najeto_km) AS najeto FROM dodavka ".
						"WHERE MONTH(datum) = $datum_mesic AND YEAR(datum) = $datum_rok ".
						"GROUP BY id_auta) AS temp ORDER BY najeto DESC";
				
					mysql_query("SET CHARACTER SET utf8");
				
					mysql_select_db('dovydata');
					$retval = mysql_query( $sql, $conn );
	
					while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
					
						echo "<tr><td>{$row['id_auta']}</td><td>{$row['spz']}</td>".
							"<td style=\"color:#d00;\">{$row['najeto']} km</td></tr>";
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