import React from 'react';
import Countries from '../../utils/countries.json'

export const GenderSelect = ({ value, onChangeAction }) => {
    if (value == null) value = "";

    return (
        <div className="col-span-1 bg-transparent">
            <label htmlFor="gender" className="w-full font-semibold">País</label>
            <select className="w-full pt-1 pb-2 border-b-2 bg-transparent border-b-dark-grey dark:border-b-light"
                name="gender" id="gender" onChange={onChangeAction} defaultValue={value || "-"} required>
                <option value="-" disabled>Selecciona una opción</option>
                <option value="Mujer" className='w-fit text-dark-green text-pretty'>Mujer</option>
                <option value="Hombre" className='w-fit text-dark-green text-pretty'>Hombre</option>
                <option value="No binario" className='w-fit text-dark-green text-pretty'>No binario</option>
                <option value="Otro" className='w-fit text-dark-green text-pretty'>Otro</option>
                <option value="Prefiero no decir" className='w-fit text-dark-green text-pretty'>Prefiero no decir</option>
            </select>
        </div>
    )
}
