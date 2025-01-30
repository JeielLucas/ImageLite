'use client'

import { Template, RenderIf, Input, Button, FieldError, useNotification } from "@/components"
import { useState } from "react";
import {LoginForm, validationScheme, formScheme} from './formScheme'
import { useAuth } from "@/resources";
import { useFormik } from "formik";
import { useRouter } from 'next/navigation'
import { AccessToken, Credentials, User } from "@/resources/user/users.resource";

export default function LoginPage(){

    const [loading, setLoading] = useState<boolean>(false);
    const [newUserState, setNewUserState] =  useState<boolean>(false);

    const auth = useAuth();
    const notification = useNotification();
    const router = useRouter();

    const {values, handleChange, handleSubmit, errors, resetForm} = useFormik<LoginForm>({
        initialValues: formScheme,
        validationSchema: validationScheme,
        onSubmit: onSubmit
    })

    async function onSubmit(values: LoginForm){
        if(!newUserState){
            const credentials: Credentials = { email: values.email, password: values.password}
            try{
                const accessToken: AccessToken = await auth.authenticate(credentials);
                auth.initSession(accessToken);
                router.push("/galeria");
            }catch(error: any){
                const message = error?.message;
                notification.notify(message, "error");
            }
        }else{
            const user: User = { name: values.name, email: values.email, password: values.password }
            try{
                await auth.save(user);
                notification.notify("Success on saving user!", "success");
                resetForm();
                setNewUserState(false);
            }catch(error: any){
                const message = error?.message;
                notification.notify(message, "error")
            }
        }
    }

    return(
        <Template loading={loading}>
            <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-2 lg:px-8">
                
                <div className="sm:mx-auto sm:w-full sm:max-w-sm">
                    <h2 className="mt-10 text-center text-1xl font-bold leading-9 tracking-tight text-gray-900">
                        { newUserState ? 'Create New User' : 'Login to Your Account' }
                    </h2>
                </div>

                <div className="mt-10 sm:mx-auto sm:w-full sm:max-w:sm">
                    <form onSubmit={handleSubmit} className="space-y-2">
                        <RenderIf condition={newUserState}>
                            <div>
                                <label className="block text-sm font-medium leading-6 text-gray-900">Name: </label>
                            </div>
                            <div className="mt-2">
                                <Input 
                                style="w-full" 
                                id="name"
                                onChange={handleChange}
                                value={values.name}
                                />
                            </div>
                            <FieldError error={errors.name}/>
                        </RenderIf>

                        <div>
                            <label className="block text-sm font-medium leading-6 text-gray-900">Email: </label>
                        </div>
                        <div className="mt-2">
                            <Input 
                                type="email"
                                style="w-full" 
                                id="email"
                                onChange={handleChange}
                                value={values.email}
                            />
                            <FieldError error={errors.email}/>
                        </div>

                        <div>
                            <label className="block text-sm font-medium leading-6 text-gray-900">Password: </label>
                        </div>
                        <div className="mt-2">
                            <Input 
                                type="password"
                                style="w-full" 
                                id="password"
                                onChange={handleChange}
                                value={values.password}
                            />
                            <FieldError error={errors.password}/>
                        </div>

                        <RenderIf condition={newUserState}>
                            <div>
                                <label className="block text-sm font-medium leading-6 text-gray-900">Repeat Password: </label>
                            </div>
                            <div className="mt-2">
                                <Input 
                                    type="password"
                                    style="w-full" 
                                    id="passwordMatch"
                                    onChange={handleChange}
                                    value={values.passwordMatch}
                                />
                                <FieldError error={errors.passwordMatch}/>
                            </div>
                        </RenderIf>

                        <div className="flex justify-center space-x-6">
                            <RenderIf condition={newUserState}>
                                <Button 
                                    type="button" 
                                    style="bg-red-700 hover:bg-red-500" 
                                    label="Cancel" 
                                    onClick={event => setNewUserState( false )}
                                />
                                <Button 
                                    type="submit" 
                                    style="bg-indigo-700 hover:bg-indigo-500 mx-2" 
                                    label="Save"
                                />
                            </RenderIf>

                            <RenderIf condition={!newUserState}>
                                <Button 
                                    type="button" 
                                    style="bg-red-700 hover:bg-red-500" 
                                    label="Sign Up"
                                    onClick={event => setNewUserState( true )}
                                />
                                <Button 
                                    type="submit" 
                                    style="bg-indigo-700 hover:bg-indigo-500 mx-2" 
                                    label="Login"
                                />
                            </RenderIf>
                        </div>
                    </form>
                </div>
            </div>
        </Template>
    )
}