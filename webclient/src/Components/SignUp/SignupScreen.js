import {Link, useNavigate} from 'react-router-dom';
import InputField from '../Login/InputField';
import {useState} from "react";
import SignUpToast from './SignUpToast'

function SignupScreen() {

    const navigate = useNavigate();
    const [passwordError, setPasswordError] = useState("");
    const [confirmationError, setConfirmationError] = useState("");
    const [imageError, setImageError] = useState("")
    const [image, setImage] = useState('');
    const [spinner, setSpinner] = useState(false)
    const [showToast, setShowToast] = useState(false)

    const image_pattern = /\.(jpg|JPG|jpeg|JPEG|png|PNG|gif|GIF|bmp|BMP)$/

    function handleToast(){
        setShowToast(false)
    }

    // convert an image to base64
    function convertToBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => {
                const base64String = reader.result;
                resolve(base64String);
            };
            reader.onerror = (error) => {
                reject(error);
            };
            reader.readAsDataURL(file);
        });
    }

    function mouseDown() {
        document.getElementById("register-btn").style.backgroundColor = "rgba(255, 255, 255, 0.5)";
    }

    function mouseUp() {
        document.getElementById("register-btn").style.backgroundColor = "rgba(255, 255, 255, 0.7)";
    }

    function handleSpinner() {
        if (!image) {
            setSpinner(true)
        }
    }

    function handleImage(event) {
        if (event.target.files[0]) {
            setImageError("")
            const file = event.target.files[0]
            if (image_pattern.test(file.name)) {
                setSpinner(false);
                setImage(URL.createObjectURL(file));
            } else {
                setImageError(" Please choose a valid image");
            }
        } else {
            setImage('');
            setSpinner(true);
        }
    }

    async function addAccount(event) {
        event.preventDefault();
        // create a json for the new account
        const account = {
            username: event.target.username.value,
            password: event.target.password.value,
            displayName: event.target.d_name.value,
            profilePic: await convertToBase64(event.target.picture.files[0])
        }
        const confirmation = event.target.confirmation.value;

        setPasswordError("");
        setConfirmationError("");

        const password_pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/

        if (!password_pattern.test(account.password)) {
            setPasswordError(" Minimum 8 characters, at least 1 uppercase and 1 lowercase letters, 1 number and 1 special character");
            event.target.password.value = '';
            event.target.confirmation.value = '';
            return;
        }

        if (account.password !== confirmation) {
            setConfirmationError(" Passwords don't match");
            event.target.password.value = '';
            event.target.confirmation.value = '';
            return;
        }
        // send a signup request to the server with the new account
        try {
            const response = await fetch('http://localhost:5000/api/Users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(account)
            });
            // Handle successful response here
            if(response.status === 200){
                navigate("/");
            }
            // Handle duplication
            if(response.status === 409){
                // alert("This user already exists")
                setShowToast(true)
            }
        } catch (error) {
            // Handle error here
            console.error('Error creating user:', error.message);
        }
    }

    return (
        <div className="screen-page">
            <div className="screen-box">
                <form onSubmit={addAccount}>
                    <div className="header">Sign Up</div>
                    {showToast && <SignUpToast toastFunc={handleToast} render={showToast}/>}
                    <InputField type="text" name="username" placeholder="Username"
                                icon={<i className="bi bi-person-fill"></i>} required/>
                    <InputField type="password" name="password" placeholder="Password" required/>
                    {passwordError && <div className="error-message">{passwordError}</div>}
                    <InputField type="password" name="confirmation" placeholder="Confirm Password" required/>
                    {confirmationError && <div className="error-message">{confirmationError}</div>}
                    <InputField type="text" name="d_name" placeholder="Display Name" required/>
                    <div className="input-field">
                        <input className="fileInput" name="picture" type="file" accept="image/*"
                               onChange={handleImage} onClick={handleSpinner} required/>
                        <label htmlFor="upload">
                            {image && <img src={image} className="rounded-circle preview_img" alt="user_img"></img>}
                            {spinner && <div className="spinner-border preview_img" role="status"><span
                                className="visually-hidden">Loading...</span></div>}
                        </label>
                    </div>
                    {imageError && <div className="error-message">{imageError}</div>}
                    <div className="input-field">
                        <input type="submit" className="submit" id="register-btn" onMouseUp={mouseUp}
                               onMouseDown={mouseDown} value="Register"/>
                    </div>
                    <div className="sign-in">
                        <p>Already have an account? <Link to="/">Login Here</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default SignupScreen;