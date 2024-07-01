function InputField({type, placeholder, icon, name}) {
    return (
        <div className="input-field">
            <input type={type} name={name} className="input-nochat" placeholder={placeholder} required/>
            <span className="icon">{icon}</span>
        </div>
    );
}

export default InputField;