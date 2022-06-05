<c:import url="../layout/app.jsp">
    <c:param name="content">
        <div id="main">
            <a id="addButton" class="green-button" href="add_note.html">Add a note</a>

            <?php
                $query = mysql_query("SELECT * FROM notes ORDER BY id DESC");
                $notes = '';
                $left='';
                $top='';
                $zindex='';
                while($row=mysql_fetch_assoc($query))
                {
                    // The xyz column holds the position and z-index in the form 200x100x10:
                    list($left,$top,$zindex) = explode('x',$row['xyz']);
                    $notes.= '
                        <div class="note '.$row['color'].'" style="left:'.$left.'px;top:'.$top.'px;  z-index:'.$zindex.'">
                        '.htmlspecialchars($row['text']).'
                        <div class="author">'.htmlspecialchars($row['name']).'</div>
                        <span class="data">'.$row['id'].'</span>
                    </div>';
                }
            ?>
        </div>

        <h3 class="popupTitle">Add a new note</h3>

        <!-- The preview: -->
        <div id="previewNote" class="note yellow" style="left:0;top:65px;z-index:1">
        <div class="body"></div>
        <div class="author"></div>
        <span class="data"></span>
        </div>

        <div id="noteData"> <!-- Holds the form -->
        <form action="" method="post" class="note-form">

        <label for="note-body">Text of the note</label>
        <textarea name="note-body" id="note-body" class="pr-body" cols="30" rows="6"></textarea>

        <label for="note-name">Your name</label>
        <input type="text" name="note-name" id="note-name" class="pr-author" value="" />

        <label>Color</label> <!-- Clicking one of the divs changes the color of the preview -->
        <div class="color yellow"></div>
        <div class="color blue"></div>
        <div class="color green"></div>

        <!-- The green submit button: -->
        <a id="note-submit" href="" class="green-button">Submit</a>

        </form>
        </div>

        <? php
            // Escaping the input data:
            $author = mysql_real_escape_string(strip_tags($_POST['author']));
            $body = mysql_real_escape_string(strip_tags($_POST['body']));
            $color = mysql_real_escape_string($_POST['color']);
            $zindex = (int)$_POST['zindex'];

            /* Inserting a new record in the notes DB: */
            mysql_query('   INSERT INTO notes (text,name,color,xyz)
            VALUES ("'.$body.'","'.$author.'","'.$color.'","0x0x'.$zindex.'")');

            if(mysql_affected_rows($link)==1)
            {
                // Return the id of the inserted row:
                echo mysql_insert_id($link);
            }
            else echo '0';
        ?>
    </c:param>
</c:import>

<!-- JavaScript -->>
<script>

</script>