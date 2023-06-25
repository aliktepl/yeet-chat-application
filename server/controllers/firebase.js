import * as admin from "firebase-admin"
const {getMessaging} = require("firebase-admin/messaging")
const {initializeApp} = require("firebase-admin/app")
const serviceAccount = require("ap2-ex3-636e2-firebase-adminsdk-n3pii-295cc3476e.json")
const {response} = require("express")

const firebase = initializeApp({
    credential: admin.credential.cert(serviceAccount),
    projectId: "ap2-ex3-636e2"
})

const users = new Map()

function sendMessage(registrationToken, title, body) {
    const message = {
        "token" : registrationToken,
        "notification" : {
            "title" : title,
            "body" : body
        }
    }

    getMessaging().send(message).then((response) => {
        console.log("Successfully sent message: ", response)
    }).catch((error) => {
        console.log("Error sending message:", error)
    })
}

export {sendMessage, users}
