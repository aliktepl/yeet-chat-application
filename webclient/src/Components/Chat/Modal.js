import {useRef} from "react";

function Modal(props) {
    const inputRef = useRef(null);

    const handleButtonClick = () => {
        if (inputRef.current.value.length > 0) {
            props.onButtonClick(inputRef.current.value);
            inputRef.current.value = "";
        }
    };


    return (
        <div
            className="modal fade"
            id="exampleModal"
            tabIndex={-1}
            aria-labelledby="exampleModalLabel"
            aria-hidden="true"
        >
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalLabel">
                            Add new contact
                        </h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>
                    </div>
                    <div className="modal-body">
                        <input type="text" className="input" ref={inputRef} placeholder="Contact's Identifier"
                               required/>
                    </div>
                    <div className="modal-footer">
                        <button
                            className="btn btn-primary"
                            onClick={handleButtonClick}
                            data-bs-dismiss="modal"
                        >
                            Add Contact
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Modal;
