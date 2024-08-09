export const TextInput = ({ label, inputName, inputId, inputPlaceholder, className, required }) => {
    return (
        <div className={`${className} w-full`}>
            <label htmlFor={inputId} className="w-full font-semibold">{label}</label>
            <div className="pt-1 pb-2 border-b-2 border-b-dark-grey dark:border-b-light">
                <input type="text" name={inputName} id={inputId} placeholder={inputPlaceholder} required={required}
                    className="w-full bg-transparent active:outline-light-green/50 focus-visible:outline-light-green/50" />
            </div>
        </div>
    )
}
