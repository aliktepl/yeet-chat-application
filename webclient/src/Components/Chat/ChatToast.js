
import { useEffect } from 'react';

const ChatToast = (params) => {

    useEffect(() => {
        const timer = setTimeout(() => {
            params.toastFunc()
        }, 3000);

        return () => clearTimeout(timer);
    }, []);

    return (
        <>
            {params.render && (
                <div
                    className="toast chat-toast align-items-center text-white bg-danger border-0 show position-fixed top-0 start-50 translate-middle-x"
                    role="alert"
                    aria-live="assertive"
                    aria-atomic="true"
                    // style={{top: "19%"}}
                >
                    <div className="d-flex">
                        <div className="toast-body">
                            User not found.
                        </div>
                        <button
                            type="button"
                            className="btn-close btn-close-white me-2 m-auto"
                            onClick={params.toastFunc}
                            data-bs-dismiss="toast"
                            aria-label="Close"
                        ></button>
                    </div>
                </div>
            )}
        </>
    );
}

export default ChatToast;

