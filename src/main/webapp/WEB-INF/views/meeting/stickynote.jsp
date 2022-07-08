<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<c:set var="actTod" value="${ForwardConst.ACT_TOD.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <!-- タイマー -->
        <c:import url="_timer.jsp" />
         <input class= "idea-btn" type="button" onclick="location.href='<c:url value='?action=${actTod}&command=${commNew}&meeting_id=${meeting.id}' />'" value="次へ">
        <br />
        <!-- 付箋 -->
        <button class="add-button" onmousedown="addNote()">+</button>
    </c:param>
</c:import>

<!-- JavaScript -->
<script>
    document.onmousemove = snapNote; //マウスが動いた時は、選択された付箋をマウスの位置まで動かす
    document.ontouchmove = snapNoteTouch; //Logic for snapping to touch is slightly different than mouse
    document.onmouseup = placeNote; //When the mouse comes up, place the selected note where the user chose
    document.ontouchend = placeNote; //When the touch is released, place the note
    document.onmousedown = clearMenus; //Clear menus when the mouse is clicked to the side
    window.addEventListener('load', loadNotes); // When document has loaded, load any notes that are in local storage
    if ('onvisibilitychange' in document)
        document.addEventListener('visibilitychange', storeNotes);
    else window.addEventListener('pagehide', storeNotes);

    let notesCount = 0; //Used to give a unique id to each note

    /**
     * addNote：新しい付箋を作成してdocumentに追加
     */
    function addNote(title = '', content = '', color = '') {
        console.log('Add button pressed');

        //note containerを作成
        let note = document.createElement('div');
        note.onmousedown = selectNote;
        note.ontouchstart = selectNote;
        note.className = 'note';
        note.style.backgroundColor = color;

        //付箋の内容を記載するtext boxを作成
        let textBox = document.createElement('textarea');
        textBox.placeholder = 'Write your note here';
        textBox.className = 'note-content';
        textBox.onkeydown = keyDown;
        textBox.value = content;
        note.appendChild(textBox);

        //付箋のボタンを作成
        let optionButton = document.createElement('button');
        optionButton.className = 'option-button';
        optionButton.textContent = '. . .';
        optionButton.onmousedown = noteMenu;
        optionButton.ontouchstart = noteMenu;
        note.appendChild(optionButton);

        note.id = ++notesCount;

        document.body.appendChild(note); //付箋を追加

        titleInput.focus(); //新しい付箋のタイトルにフォーカスをセット
    }

    let selectedNote = null; //動かすためにユーザがクリックした付箋

    /**
     * selectNote sets the selected note to the one the user clicks on.選択した付箋をユーザがクリックした付箋にセットする
     */
    function selectNote() {
        selectedNote = this;
    }

    let noteCopy = {}; //実際に動かされた付箋のコピー
    let mouseDidMove = false; // マウスが付箋を動かすくらい動いたかどうか
    let currentSwap = null; //直近に選択された付箋と交換された付箋

    /**
     * 選択された付箋をマウスの位置に動かし、選択された付箋が他の付箋の上を通った時、付箋を交換する
     * @param event マウスイベント
     */
    function snapNote(event) {
        if (selectedNote !== null) {
            //選択された付箋があるか確認する

            let mouseMovement = Math.sqrt(event.movementX ** 2 + event.movementY ** 2); // マウスが動いた合計距離

            if (!mouseDidMove && mouseMovement > 4) {
                // マウスが一定の距離を動いたか確認する

                console.log('Mouse moved');

                selectedNote.style.visibility = 'hidden'; // 実際に選択された付箋を隠す

                currentSwap = selectedNote;

                noteCopy = copyNote(selectedNote); //動かすための付箋のコピーを作成する
                noteCopy.style.position = 'fixed';

                document.body.appendChild(noteCopy); //コピーの付箋を追加する

                //選択された付箋をマウスの位置に動かす
                noteCopy.style.top = event.clientY - noteCopy.offsetHeight / 2 + 'px';
                noteCopy.style.left = event.clientX - noteCopy.offsetWidth / 2 + 'px';

                mouseDidMove = true;
            } else if (mouseDidMove) {
                //選択された付箋をマウスの位置に動かす
                noteCopy.style.top = event.clientY - noteCopy.offsetHeight / 2 + 'px';
                noteCopy.style.left = event.clientX - noteCopy.offsetWidth / 2 + 'px';

                let notes = document.getElementsByClassName('note'); // Get all the notes in the document

                for (let i = 0; i < notes.length; i++) {
                    // Loop through the notes
                    let rect = notes[i].getBoundingClientRect(); // Get the bounding rectangle to know the positon of the note

                    //付箋をつかむ
                    if (
                        currentSwap !== null &&
                        !noteCopy.id.includes(notes[i].id) &&
                        notes[i].id !== currentSwap.id
                    ) {
                        //付箋が異なる付箋か確認する
                        if (
                            event.clientX > rect.left &&
                            event.clientX < rect.right &&
                            event.clientY > rect.top &&
                            event.clientY < rect.bottom
                        ) {
                            //マウスが該当の付箋の上にあるか確認する
                            if (notes[i].style.position !== 'fixed') {
                                // Check if note is being animated付箋がアニメーション化されているか確認する
                                console.log('Selected: ' + noteCopy.id);
                                console.log('Swap with: ' + notes[i].id);

                                //動かす前の付箋の位置を、付箋の位置交換のために取得する
                                let oldRects = new Map(); //Map for old note positions for animating
                                for (let i = 0; i < notes.length; i++) {
                                    if (!notes[i].id.includes('copy')) {
                                        let oldRect = notes[i].getBoundingClientRect();
                                        oldRects.set(notes[i].id, oldRect);
                                    }
                                }

                                currentSwap.style.visibility = 'visible'; //古い付箋を表示させる
                                checkOverflow(currentSwap.children[1]); //必要であれば付箋のサイズを変更

                                swapNotes(selectedNote, currentSwap); //全回の位置交換を元に戻す
                                currentSwap = notes[i]; //currentSwapを更新
                                swapNotes(selectedNote, currentSwap); //新しい交換を実行

                                currentSwap.style.visibility = 'hidden'; //新しい交換を隠す

                                animateReorder(oldRects, 300);
                            }
                        } //End if
                    } //End if
                } //End for
            } //End else
        } //End if
    } //End snapNote

    /**
     * 選択された付箋をタッチした位置まで動かし、ユーザが他の付箋を飛び越えた時付箋を交換する
     * @param event タッチイベント
     */
    function snapNoteTouch(event) {
        if (selectNote !== null) {
            if (!mouseDidMove) {
                // マウスが一定の距離を動いたか確認する

                console.log('Mouse moved');

                selectedNote.style.visibility = 'hidden'; // 実際に選択された付箋を隠す

                currentSwap = selectedNote;

                noteCopy = copyNote(selectedNote); //動かすための付箋のコピーを作成する
                noteCopy.style.position = 'fixed';

                document.body.appendChild(noteCopy); //コピーの付箋を追加する

                //選択された付箋をマウスの位置に動かす
                noteCopy.style.top =
                    event.touches[0].clientY - noteCopy.offsetHeight / 2 + 'px';
                noteCopy.style.left =
                    event.touches[0].clientX - noteCopy.offsetWidth / 2 + 'px';

                mouseDidMove = true;
            } else if (mouseDidMove) {
                //選択された付箋をマウスの位置に動かす
                noteCopy.style.top =
                    event.touches[0].clientY - noteCopy.offsetHeight / 2 + 'px';
                noteCopy.style.left =
                    event.touches[0].clientX - noteCopy.offsetWidth / 2 + 'px';

                let notes = document.getElementsByClassName('note'); //すべての付箋を取得する

                for (let i = 0; i < notes.length; i++) {
                    // Loop through the notes

                    let rect = notes[i].getBoundingClientRect(); // Get the bounding rectangle to know the positon of the noteその付箋の位置を知るために

                    //付箋を交換する
                    if (
                        currentSwap !== null &&
                        !noteCopy.id.includes(notes[i].id) &&
                        notes[i].id !== currentSwap.id
                    ) {
                        //付箋が異なる付箋か確認する
                        if (
                            event.touches[0].clientX > rect.left &&
                            event.touches[0].clientX < rect.right &&
                            event.touches[0].clientY > rect.top &&
                            event.touches[0].clientY < rect.bottom
                        ) {
                            //マウスが該当の付箋の上にあるか確認する
                            if (notes[i].style.position !== 'fixed') {
                                console.log('Selected: ' + noteCopy.id);
                                console.log('Swap with: ' + notes[i].id);

                                //動かす前の付箋の位置を、付箋の位置交換のために取得する
                                let oldRects = new Map(); //Map for old note positions for animating
                                for (let i = 0; i < notes.length; i++) {
                                    if (!notes[i].id.includes('copy')) {
                                        let oldRect = notes[i].getBoundingClientRect();
                                        oldRects.set(notes[i].id, oldRect);
                                    }
                                }

                                currentSwap.style.visibility = 'visible'; //元の付箋を表示する
                                checkOverflow(currentSwap.children[1]); //テキストボックスのサイズを変更うｓる

                                swapNotes(selectedNote, currentSwap); //交換を元に戻す
                                currentSwap = notes[i]; //currentSwapを更新する
                                swapNotes(selectedNote, currentSwap); //新しい交換を実行する

                                currentSwap.style.visibility = 'hidden'; //新しい交換を隠す

                                animateReorder(oldRects, 300);
                            }
                        } //End if
                    } //End if
                } //End for
            } //End else
        } //End If
    } //End snapNoteTouch

    /**
     * 選択された付箋を適切な位置に置く
     */
    function placeNote() {
        if (selectedNote !== null) {
            //選択された付箋があるか確認する
            selectedNote.style.visibility = 'visible';
            checkOverflow(selectedNote.children[1]);
            selectedNote = null;

            if (mouseDidMove) {
                noteCopy.remove();
                mouseDidMove = false;
            }

            if (currentSwap !== null) {
                currentSwap.style.visibility = 'visible';
                checkOverflow(currentSwap.children[1]);
                currentSwap = null;
            }
        }
    }

    /**
     * それぞれの付箋の内容とプロパティを交換する
     * @param {HTMLDivElement} note1 The 交換対象の１つめの付箋
     * @param {HTMLDivElement} note2 交換対象の２つめの付箋
     */
    function swapNotes(note1, note2) {
        //1つめの付箋の値を保存
        let title1 = note1.children[0].value;
        let content1 = note1.children[1].value;
        let id1 = note1.id;
        let height1 = note1.children[1].style.height;
        let color1 = note1.style.backgroundColor;

        //1つめの付箋の値を更新
        note1.children[0].value = note2.children[0].value;
        note1.children[1].value = note2.children[1].value;
        note1.children[1].style.height = note2.children[1].style.height;
        note1.id = note2.id;
        note1.style.backgroundColor = note2.style.backgroundColor;

        //2つめの付箋の値を更新
        note2.children[0].value = title1;
        note2.children[1].value = content1;
        note2.children[1].style.height = height1;
        note2.id = id1;
        note2.style.backgroundColor = color1;
    }

    /**
     * 内容とプロパティをコピーして、コピーを返却する
     * @param {HTMLDivElement} originalNote
     * @returns {HTMLDivElement} 元の付箋のコピー
     */
    function copyNote(originalNote) {
        let noteCopy = document.createElement('div');
        noteCopy.className = 'note';
        noteCopy.innerHTML = originalNote.innerHTML;
        noteCopy.children[0].value = originalNote.children[0].value;
        noteCopy.children[1].value = originalNote.children[1].value;
        noteCopy.id = originalNote.id + 'copy';

        let color = originalNote.style.backgroundColor;

        noteCopy.style.backgroundColor = color;

        noteCopy.style.animationName = 'none'; //Remove fade-in animation

        return noteCopy;
    }

    /**
     * キーが押された時に付箋のテキストボックスの文字数オーバーをチェックする
     */
    function keyDown() {
        checkOverflow(this);
    }

    /**
     * 付箋のテキストボックスがテキストに合わせてサイズを変更する必要があるのかをチェックする
     * @param {HTMLTextAreaElement} textBox
     */
    function checkOverflow(textBox) {
        textBox.style.height = '';
        while (textBox.scrollHeight > textBox.clientHeight) {
            textBox.style.height = textBox.clientHeight + 2 + 'px';
        }
    }

    /**
     * 付箋のオプションメニューを作成する
     */
    function noteMenu() {
        console.log('option button pressed');

        let menus = document.getElementsByClassName('note-menu'); // Get all menus

        for (let i = 0; i < menus.length; i++) {
            menus[i].remove();
        }

        let noteMenu = document.createElement('div');
        noteMenu.className = 'note-menu';

        let colors = [
            // 付箋の色（９色）
            'lightgoldenrodyellow',
            'lightblue',
            'lightgreen',
            'lightpink',
            'lightcoral',
            'lightskyblue',
            'lightsalmon',
            'plum',
            'lightseagreen',
        ];

        // ９色のボタンを作成
        colors.forEach((color) => {
            let colorOption = document.createElement('button');
            colorOption.className = 'color-option';
            colorOption.style.backgroundColor = color;
            colorOption.onmousedown = setColor;
            colorOption.ontouchstart = setColor;
            noteMenu.appendChild(colorOption);
        });

        // 削除ボタンを作成
        let deleteButton = document.createElement('div');
        deleteButton.className = 'delete-note';
        deleteButton.onmousedown = () => {
            setTimeout(deleteNote.bind(deleteButton), 155);
        }; //Add a delay to let user see button press
        let deleteText = document.createElement('p');
        deleteText.textContent = 'Delete';
        deleteText.className = 'delete-text';
        deleteButton.appendChild(deleteText);
        let deleteIcon = document.createElement('img');
        deleteIcon.className = 'delete-icon';
        deleteButton.appendChild(deleteIcon);
        noteMenu.appendChild(deleteButton);

        this.parentNode.appendChild(noteMenu); // Add the menu to the note
    }

    /**
     * 押されたボタンの色の付箋の色をセット
     */
    function setColor() {
        console.log('color button pressed');

        let note = this.parentNode.parentNode;
        let newColor = this.style.backgroundColor;

        note.style.backgroundColor = newColor;
    }

    /**
     * マウスが載っていないすべてのメニューをクリアする
     * @param {MouseEvent} event
     */
    function clearMenus(event) {
        console.log('Clear menus');
        console.log('ClientX: ' + event.clientX);
        console.log('ClientY: ' + event.clientY);

        let noteMenus = document.getElementsByClassName('note-menu'); // すべてのメニューを取得

        for (let i = 0; i < noteMenus.length; i++) {
            // Loop through the menus
            let rect = noteMenus[i].getBoundingClientRect(); // Get the bounding rectangle to know the position

            // マウスがメニューの上になければ取り除く
            if (
                event.clientX < rect.left ||
                event.clientX > rect.right ||
                event.clientY < rect.top ||
                event.clientY > rect.bottom
            ) {
                if (noteMenus[i].id == 'active') {
                    //Remove the note only on a second click to account for clicking the option button付箋を取り除く
                    noteMenus[i].remove();
                } else {
                    noteMenus[i].id = 'active';
                } //End else
            } //End if
        } //End for
    } //End clearMenus

    /**
     * deleteNote deletes a note whose delete button was pressed and initiates the reordering animation.
     */
    function deleteNote() {
        let thisNote = this.parentNode.parentNode;

        let notes = document.getElementsByClassName('note');
        let oldRects = new Map(); // Initialize an array for the old note positions

        // Collect all the current note positions
        for (let i = 0; i < notes.length; i++) {
            let rect = notes[i].getBoundingClientRect();
            oldRects.set(notes[i].id, rect);
        }

        thisNote.remove();

        animateReorder(oldRects, 300); // Using the old positions, animate the reording of the notes over the specified time
    }

    /**
     * Takes the old positions of elements and animates them to their new positions
     * @param {Map} oldRects dictionary of note id's and their rects
     * @param {number} duration
     */
    function animateReorder(oldRects, duration) {
        console.log(oldRects);
        let notes = document.getElementsByClassName('note'); // Get all the notes
        let newRects = new Map(); // Initialize array for collecting new positions

        // Collect the new positions
        for (let i = 0; i < notes.length; i++) {
            let newRect = notes[i].getBoundingClientRect();
            newRects.set(notes[i].id, newRect);
        }

        // Set initial positions
        let offsetX = parseFloat(window.getComputedStyle(notes[0]).marginLeft);
        let offsetY = parseFloat(window.getComputedStyle(notes[0]).marginTop);
        let width = parseFloat(window.getComputedStyle(notes[0]).width);
        for (let i = 0; i < notes.length; i++) {
            if (oldRects.has(notes[i].id) && newRects.has(notes[i].id)) {
                notes[i].style.position = 'fixed';
                notes[i].style.left = oldRects.get(notes[i].id).left - offsetX + 'px';
                notes[i].style.top = oldRects.get(notes[i].id).top - offsetY + 'px';
                notes[i].style.width = width + 'px';
            }
        }

        let timePassed = 0; // Time passed since animation began, in ms
        let lastFrame = Date.now(); //The timestamp of the previous frame

        // This function animates a single frame of the animation and then passed itself to `requestAnimationFrame`.
        function animateFrame() {
            let deltaT = Date.now() - lastFrame; // Time difference between now and the last frame
            timePassed += deltaT;
            lastFrame = Date.now();

            // Update the positions of the notes
            for (let i = 0; i < notes.length; i++) {
                if (oldRects.has(notes[i].id) && newRects.has(notes[i].id)) {
                    let deltaX =
                        ((newRects.get(notes[i].id).left - oldRects.get(notes[i].id).left) *
                            deltaT) /
                        duration;
                    let deltaY =
                        ((newRects.get(notes[i].id).top - oldRects.get(notes[i].id).top) *
                            deltaT) /
                        duration;

                    notes[i].style.left = parseFloat(notes[i].style.left) + deltaX + 'px';
                    notes[i].style.top = parseFloat(notes[i].style.top) + deltaY + 'px';
                }
            }

            // Check if the proper amount of time has passed
            if (timePassed < duration) {
                requestAnimationFrame(animateFrame);
            } else {
                for (let i = 0; i < notes.length; i++) {
                    if (oldRects.has(notes[i].id) && newRects.has(notes[i].id)) {
                        notes[i].style.position = 'relative';
                        notes[i].style.left = '0px';
                        notes[i].style.top = '0px';
                        notes[i].style.width = '';
                    } //End if
                } //End for
            } //End else
        } //End animateFrame

        animateFrame();
    }

    /**
     * @typedef Note
     * @type {object}
     * @property {string} title
     * @property {string} content
     * @property {string} color
     */
    class Note {
        constructor(title = '', content = '', color = '') {
            this.title = title;
            this.content = content;
            this.color = color;
        }
    }

    /**
     * storeNotes stores any notes that are on the
     * screen in local storage
     *
     * @returns {void}
     */
    function storeNotes() {
        /**
         * @type {HTMLDivElement[]}
         */
        const noteElements = Array.from(document.getElementsByClassName('note'));
        console.log(noteElements);

        /**
         * @type {Note[]}
         */
        const noteObjects = [];

        noteElements.forEach((note) => {
            noteObjects.push(
                new Note(
                    note.children[0].value, // Title
                    note.children[1].value, // Content
                    note.style.backgroundColor
                )
            );
        });

        console.log(noteObjects);

        localStorage.setItem('notes', JSON.stringify(noteObjects));
    }

    /**
     * loadNotes gets the notes stored in localStorage,
     * if there are any, and adds them to the document.
     *
     * @returns {void}
     */
    function loadNotes() {
        const data = localStorage.getItem('notes');

        if (data === null) return;

        /**
         * @type Note[]
         */
        const noteObjects = JSON.parse(data);

        noteObjects.forEach((note) => {
            addNote(note.title, note.content, note.color);
        });
    }
</script>
