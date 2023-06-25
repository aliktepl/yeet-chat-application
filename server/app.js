const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const http = require('http');
const { Server } = require("socket.io");
const server = http.createServer(app);
const io = new Server(server,
    {
        cors: {
            origin: '*',
            methods: ['GET', 'POST', 'DELETE']
        }
    });
const socket = require('./controllers/sockets')

app.use(bodyParser.urlencoded({extended: true}));
app.use(express.json());

const cors = require('cors');

app.use(cors());

const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV, './config');

try {
    const mongoose = require('mongoose');
    mongoose.connect(process.env.CONNECTION_STRING, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    });
} catch (error) {
    console.error('Failed to connect to MongoDB:', error);
    process.exit(1); // Terminate the application if the connection fails
}

app.use(express.static('public'));

const users = require('./routes/user');
app.use('/api/Users', users);
const tokens = require('./routes/tokens');
app.use('/api/Tokens', tokens);
const chats = require('./routes/chats');
app.use('/api/Chats', chats);

socket.Sockets(io);

server.listen(process.env.PORT);