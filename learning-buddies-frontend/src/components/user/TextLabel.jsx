export const TextLabel = ({ label, inputPlaceholder, className }) => {

    if (inputPlaceholder == null || inputPlaceholder.trim().length == 0) inputPlaceholder = "-";

    return (
        <div className={`${className} w-full`}>
            <p className="w-full font-semibold">{label}</p>
            <div className="pt-1 pb-2 border-b-2 border-b-dark-grey dark:border-b-light">
                <p className="w-full bg-transparent active:outline-light-green/50 focus-visible:outline-light-green/50">{inputPlaceholder}</p>
            </div>
        </div>
    )
}
