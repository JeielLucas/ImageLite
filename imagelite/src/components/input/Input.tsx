interface InputProps{
    style?: string;
    onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
    placeholder? : string;
    id?: string;
    value?: string;
    type?: string;
}

export const Input: React.FC<InputProps> = ({
    style, type = "text", ...rest
} : InputProps) => {
    return(
        <input type={type}
                /*
                SPREAD OPERATOR
                Todas as propriedades do props são atribuidas a propriedades de nome
                igual no component, ex: onChange (do props) é atribuida ao
                onChange (do input) pois os nomes são iguais
                */
                {...rest}
               className={`${style} border px-3 py-2 rounded-lg text-gray-900`}
        />
    )
}