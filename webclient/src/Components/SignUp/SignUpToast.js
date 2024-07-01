
import { useEffect } from 'react';

const SignUpToast = (params) => {

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
                    className="toast signup-toast align-items-center text-white bg-danger border-0 show position-fixed start-50 translate-middle-x"
                    role="alert"
                    aria-live="assertive"
                    aria-atomic="true"
                >
                    <div className="d-flex">
                        <div className="toast-body">
                            This user already exists.
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

export default SignUpToast;

