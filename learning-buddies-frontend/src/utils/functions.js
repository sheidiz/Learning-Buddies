export const normalizeError = (error) => {
    let message = error.message;

    message = message.replace('["', '').replace('"]', '');

    return message.toString();
}