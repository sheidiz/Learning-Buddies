export const TextareaInput = ({ label, value, inputName, inputPlaceholder, className, required, onChangeAction }) => {
    if (value == null) value = "";
    if(inputPlaceholder == null ) inputPlaceholder = "";

    return (
        <div className={`${className} w-full`}>
            <label htmlFor={inputName} className="w-full font-semibold">{label}</label>
            <textarea name={inputName} id={inputName} placeholder={inputPlaceholder} required={required} onChange={onChangeAction} defaultValue={value}
                className="w-full min-h-20 mt-1 p-2 bg-transparent rounded-lg border-2 border-dark-grey dark:border-light active:outline-light-green/50 focus-visible:outline-light-green/50">
            </textarea>
        </div>
    )
}
