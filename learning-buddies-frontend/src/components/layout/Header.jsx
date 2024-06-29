import { FaUserLarge } from "react-icons/fa6";
import LogoIcon from "../../assets/images/icons/logoicon.png";
import { Disclosure, DisclosureButton, DisclosurePanel, Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { MdAccountCircle, MdClose, MdMenu, MdMenuBook } from "react-icons/md";

const user = { name: 'Sheila' };
let navigation = [
    { name: 'Inicio', href: '#', current: true, type: 'none' },
    { name: 'Buddies', href: '#', current: false, type: 'registered' },
    { name: 'Preguntas Frecuentes', href: '#', current: false, type: 'none' },
    { name: 'Recursos', href: '#', current: false, type: 'none' },
]
if (user == null) navigation = navigation.filter((nav) => nav.type.includes("none"));

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export const Header = () => {
    return (
        <Disclosure as="nav" className="bg-light dark:bg-dark">
            {({ open }) => (
                <>
                    <div className="mx-auto xl:max-w-7xl px-2 lg:px-8">

                        <div className="relative flex h-16 items-center justify-between">
                            <div className="absolute -inset-y-0 right-0 flex items-center md:hidden">
                                {/* Mobile menu button*/}
                                <DisclosureButton className="relative inline-flex items-center justify-center p-2 text-gray-400 focus:outline-none">
                                    <span className="absolute -inset-0.5" />
                                    <span className="sr-only">Open main menu</span>
                                    {open ? (
                                        <MdClose className="block h-6 w-6 text-dark-green dark:text-dm-light-green hover:text-medium-green dark:hover:text-dm-medium-green" aria-hidden="true" />
                                    ) : (
                                        <MdMenu className="block h-6 w-6 text-dark-green dark:text-dm-light-green hover:text-medium-green dark:hover:text-dm-medium-green" aria-hidden="true" />
                                    )}
                                </DisclosureButton>
                            </div>
                            <div className="ms-2 md:ms-0 flex flex-1 items-center justify-start md:items-stretch md:justify-start">
                                <div className="flex flex-shrink-0 items-center">
                                    <MdMenuBook className="text-2xl md:text-4xl text-dark-green dark:text-dm-light-green" />
                                    <span className="block ml-2 md:ml-1 text-2xl md:text-3xl text-dark-green dark:text-dm-light-green font-semibold">Learning Buddies</span>
                                </div>
                                <div className="hidden md:ml-2 lg:ml-20 md:block">
                                    <div className="flex space-x-2 lg:space-x-4">
                                        {navigation.map((item) => (
                                            <a
                                                key={item.name}
                                                href={item.href}
                                                className={classNames(
                                                    item.current ? 'text-medium-green font-bold' : 'text-light-green hover:text-medium-green',
                                                    'rounded-3xl px-3 md:px-2 lg:px-4 py-2 text-md md:text-lg font-medium',
                                                )}
                                                aria-current={item.current ? 'page' : undefined}
                                            >
                                                {item.name}
                                            </a>
                                        ))}
                                    </div>
                                </div>
                            </div>
                            <div className="absolute inset-y-0 right-0 flex items-center pr-2 md:static md:inset-auto md:ml-6 md:pr-0">

                                {/* Profile dropdown */}
                                <Menu as="div" className="hidden md:block relative ml-3">
                                    <div>
                                        <MenuButton className="relative flex rounded-full px-2 py-1 focus:outline-none border-light-green border-2 hover:bg-white">
                                            <span className="absolute -inset-1.5" />
                                            <span className="sr-only">Open user menu</span>
                                            <MdMenu className="me-1 my-auto h-full text-2xl text-light-green dark:text-dm-light-green" />
                                            <MdAccountCircle className="m-auto h-full text-3xl text-light-green dark:text-dm-light-green" />
                                        </MenuButton>
                                    </div>
                                    <MenuItems
                                        transition
                                        className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 transition focus:outline-none data-[closed]:scale-95 data-[closed]:transform data-[closed]:opacity-0 data-[enter]:duration-100 data-[leave]:duration-75 data-[enter]:ease-out data-[leave]:ease-in"
                                    >
                                        {user != null ?
                                            <>
                                                <MenuItem>
                                                    {({ focus }) => (
                                                        <a href="#" className={classNames(focus ? 'bg-gray-100' : '', 'block px-4 py-2 text-md text-gray-700')}>
                                                            Tu Perfil
                                                        </a>
                                                    )}
                                                </MenuItem>
                                                <MenuItem>
                                                    {({ focus }) => (
                                                        <a href="#" className={classNames(focus ? 'bg-gray-100' : '', 'block px-4 py-2 text-md text-gray-700')}>
                                                            Cerrar sesión
                                                        </a>
                                                    )}
                                                </MenuItem>
                                            </>
                                            : <>
                                                <MenuItem>
                                                    {({ focus }) => (
                                                        <a href="#" className={classNames(focus ? 'bg-gray-100' : '', 'block px-4 py-2 text-md text-gray-700')}>
                                                            Iniciar sesión
                                                        </a>
                                                    )}
                                                </MenuItem>
                                                <MenuItem>
                                                    {({ focus }) => (
                                                        <a href="#" className={classNames(focus ? 'bg-gray-100' : '', 'block px-4 py-2 text-md text-gray-700')}>
                                                            Registrarme
                                                        </a>
                                                    )}
                                                </MenuItem>
                                            </>}

                                    </MenuItems>
                                </Menu>
                            </div>
                        </div>

                    </div>

                    <DisclosurePanel className="md:hidden">
                        <div className="space-y-1 px-2 pb-3 pt-2">
                            {navigation.map((item) => (
                                <DisclosureButton
                                    key={item.name}
                                    as="a"
                                    href={item.href}
                                    className={classNames(
                                        item.current ? 'bg-light-green text-white' : 'text-light-green hover:bg-light-green hover:text-white',
                                        'block rounded-md px-3 py-2 text-base font-medium',
                                    )}
                                    aria-current={item.current ? 'page' : undefined}
                                >
                                    {item.name}
                                </DisclosureButton>
                            ))}
                            {
                                user != null ?
                                    (
                                        <>
                                            <a href="#" className="mt-2 border-t border-light-green block px-3 py-2 text-base font-medium text-light-green">Mi Perfil</a>
                                            <a href="#" className="block px-3 py-2 text-base font-medium text-light-green">Cerrar sesión</a>
                                        </>

                                    ) :
                                    (
                                        <>
                                            <a href="#" className="mt-2 border-t border-light-green block px-3 py-2 text-base font-medium text-light-green">Iniciar sesión</a>
                                            <a href="#" className="block px-3 py-2 text-base font-medium text-light-green">Registrarme</a>
                                        </>
                                    )
                            }

                        </div>
                    </DisclosurePanel>
                </>
            )}
        </Disclosure>
    )
}
