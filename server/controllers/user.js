const userService = require('../services/user')
const User = require('../models/user')

const createUser = async (req, res) => {
    const userExist = await User.findOne({username : req.body.username})
    if(userExist){
        res.status(409).json()
    }else{
        res.status(200).json(await userService.createUser(req.body.username, req.body.password, req.body.displayName, req.body.profilePic))
    }
};

const getUser = async (req, res) => {
    res.json(await userService.getUser(req.headers.passedName, req.params.username))
}

module.exports = {createUser, getUser}
