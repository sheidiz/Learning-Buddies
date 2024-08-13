export const TextInput = ({ label, inputName, inputPlaceholder, className, required, onChangeAction }) => {
    return (
        <div className={`${className} w-full`}>
            <label htmlFor={inputName} className="w-full font-semibold">{label}</label>
            <div className="pt-1 pb-2 border-b-2 border-b-dark-grey dark:border-b-light">
                <input type="text" name={inputName} id={inputName} placeholder={inputPlaceholder} required={required} onChange={onChangeAction}
                    className="w-full bg-transparent active:outline-light-green/50 focus-visible:outline-light-green/50" />
            </div>
        </div>
    )
}
