'use strict';
var usernamePage = document.querySelector('#userJoin');
var chatPage = document.querySelector('#chat-page');
var name = $("#userLogged").val().trim();
var waiting = document.querySelector('.waiting');
var stompClient = null;
var currentSubscription;
var topic = null;
var roomId;
var sockets = [];

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event, rId) {
    for (var s in sockets)
        sockets[s].close();
    roomId = rId;
    Cookies.set('name', name);
    let socket = new SockJS('/sock');
    stompClient = Stomp.over(socket);
    sockets.push(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}

function onConnected() {
    enterRoom(roomId);
}

function onError(error) {
    waiting.textContent = 'uh oh! service unavailable';
}

function enterRoom(newRoomId) {
    let roomId = newRoomId;
    Cookies.set('roomId', roomId);
    topic = `/chat-app/chat/${newRoomId}`;
    currentSubscription = stompClient.subscribe(`/chat-room/${roomId}`, onMessageReceived);
    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: name, type: 'JOIN'})
    );
}

function sendMessage(event, roomId) {
    let messageContent = $("#message").val().trim();
    topic = `/chat-app/chat/${roomId}`;
    if (messageContent && stompClient) {
        let chatMessage = {
            sender: name,
            content: messageContent,
            type: 'CHAT'
        };
        stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
        document.querySelector('#message').value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');
    let divCard = document.createElement('div');
    let check = false;
    divCard.className = 'card card-messagebox mb-1';
    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
        check = true;
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
        check = true;
    } else {
        messageElement.classList.add('chat-message');
        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);
        messageElement.appendChild(avatarElement);
        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        let divCardBody = document.createElement('div');
        divCardBody.className = 'card-body p-0';
        divCardBody.appendChild(messageElement);
        divCard.appendChild(divCardBody);
    }
    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    let messageArea = document.querySelector('#messageArea');
    if (check) {
        messageArea.appendChild(messageElement);
    } else {
        messageArea.appendChild(divCard);
    }
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}


