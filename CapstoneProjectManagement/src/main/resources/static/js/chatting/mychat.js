'use strict';
const usernamePage = document.querySelector('#userJoin');
const chatPage = document.querySelector('#chatPage');
const name = $("#userLogged").val().trim();
const waiting = document.querySelector('.waiting');
const roomIdDisplay = document.querySelector('#room-id-display');
let stompClient = null;
let currentSubscription;
let topic = null;
let roomId;

function connect(event, rId) {
    roomId = rId;
    Cookies.set('name', name);
    let socket = new SockJS('/sock');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}

function onConnected() {
    enterRoom(roomId);
    waiting.classList.add('d-none');

}

function onError(error) {
    waiting.textContent = 'uh oh! service unavailable';
}

function enterRoom(newRoomId) {
    let roomId = newRoomId;
    Cookies.set('roomId', roomId);
    roomIdDisplay.textContent = roomId;
    topic = `/chat-app/chat/${newRoomId}`;
    currentSubscription = stompClient.subscribe(`/chat-room/${roomId}`, onMessageReceived);
    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: name, type: 'JOIN'})
    );
}

function onMessageReceived(payload) {

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
    divCard.className = 'card';
    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');
        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        let divCardBody = document.createElement('div');
        divCardBody.className = 'card-body';

        divCardBody.appendChild(messageElement);
        divCard.appendChild(divCardBody);
    }
    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);
    let messageArea = document.querySelector('#messageArea');
    messageArea.appendChild(divCard);
    messageArea.scrollTop = messageArea.scrollHeight;
}


