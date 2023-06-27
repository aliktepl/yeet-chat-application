const users = new Map()

function Sockets(io) {
    io.on('connection', (socket) => {
        const listener = (username) => {
            if (!users.has(username)) {
                users.set(username, socket.id)
            }
        }
        socket.on('newUser', listener)

        socket.on('message', (username) => {
            if (users.has(username)) {
                io.to(users.get(username)).emit('message')
            }
        })

        socket.on('disconnect', () => {
            users.forEach((socket_id, username) => {
                if (socket_id === socket.id) {
                    users.delete(username);
                }
            });
            socket.off('newUser', listener); // Remove the 'message' event listener for the specific socket
            socket.disconnect();
        });

    })
}

module.exports = {Sockets};