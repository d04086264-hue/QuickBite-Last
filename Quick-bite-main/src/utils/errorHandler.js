// import { toast } from 'react-toastify';

// /**
//  * Centralized error handler to display backend error messages
//  * @param {Error} error 
//  * @param {string} defaultMessage - Optional default message if backend doesn't provide one
//  */
// export const handleApiError = (error, defaultMessage = 'An error occurred') => {

//   if (error.response && error.response.data) {
//     const errorData = error.response.data;
 
//     if (errorData.errorMessage) {
//       toast.error(errorData.errorMessage);
//       return errorData.errorMessage;
//     }
    
//     if (errorData.message) {
//       toast.error(errorData.message);
//       return errorData.message;
//     }
    

//     if (errorData.error) {
//       toast.error(errorData.error);
//       return errorData.error;
//     }
//   }
 
//   if (error.message) {
//     toast.error(error.message);
//     return error.message;
//   }
 
//   toast.error(defaultMessage);
//   return defaultMessage;
// };

// export default handleApiError;
