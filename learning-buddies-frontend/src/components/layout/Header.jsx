import {
  Disclosure,
  DisclosureButton,
  DisclosurePanel,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
} from "@headlessui/react";
import { MdAccountCircle, MdClose, MdMenu, MdMenuBook } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

export const Header = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const navigation = [
    { name: "Inicio", href: "/", current: true, type: "none" },
    { name: "Buddies", href: "/buddies", current: false, type: "registered" },
    { name: "Preguntas Frecuentes", href: "#", current: false, type: "none" },
    { name: "Recursos", href: "#", current: false, type: "none" },
  ].filter((nav) => {
    if (user) return true;
    return nav.type === "none";
  });

  const handleLogOut = async () => {
    await logout();
    navigate("/");
  };

  return (
    <Disclosure as="nav" className="bg-light dark:bg-dark">
      {({ open }) => (
        <>
          <div className="mx-auto px-2 lg:px-8 xl:max-w-7xl">
            <div className="relative flex h-16 items-center justify-between">
              <div className="absolute -inset-y-0 right-0 flex items-center md:hidden">
                {/* Mobile menu button*/}
                <DisclosureButton className="relative inline-flex items-center justify-center p-2 text-gray-400 focus:outline-none">
                  <span className="absolute -inset-0.5" />
                  <span className="sr-only">Open main menu</span>
                  {open ? (
                    <MdClose
                      className="block h-6 w-6 text-dark-green hover:text-medium-green dark:text-dm-light-green dark:hover:text-dm-medium-green"
                      aria-hidden="true"
                    />
                  ) : (
                    <MdMenu
                      className="block h-6 w-6 text-dark-green hover:text-medium-green dark:text-dm-light-green dark:hover:text-dm-medium-green"
                      aria-hidden="true"
                    />
                  )}
                </DisclosureButton>
              </div>
              <div className="ms-2 flex flex-1 items-center justify-start md:ms-0 md:items-stretch md:justify-start">
                <div className="flex flex-shrink-0 items-center">
                  <MdMenuBook className="text-2xl text-dark-green md:text-4xl dark:text-dm-light-green" />
                  <span className="ml-2 block text-2xl font-semibold text-dark-green md:ml-1 lg:text-3xl dark:text-dm-light-green">
                    Learning Buddies
                  </span>
                </div>
                <div className="hidden md:ml-2 md:block lg:ml-20">
                  <div className="flex space-x-2 lg:space-x-4">
                    {navigation.map((item) => (
                      <a
                        key={item.name}
                        href={item.href}
                        className={classNames(
                          item.current
                            ? "font-bold text-medium-green"
                            : "text-light-green hover:text-medium-green",
                          "text-md rounded-3xl px-3 py-2 font-medium md:px-2 md:text-lg lg:px-4",
                        )}
                        aria-current={item.current ? "page" : undefined}
                      >
                        {item.name}
                      </a>
                    ))}
                  </div>
                </div>
              </div>
              <div className="absolute inset-y-0 right-0 flex items-center pr-2 md:static md:inset-auto md:ml-6 md:pr-0">
                {/* Profile dropdown */}
                <Menu as="div" className="relative ml-3 hidden md:block">
                  <div>
                    <MenuButton
                      className={`relative flex rounded-full px-2 py-1 focus:outline-none ${user == null ? "border-2 border-light-green hover:bg-white" : "bg-light-green"}`}
                    >
                      <span className="absolute -inset-1.5" />
                      <span className="sr-only">Open user menu</span>
                      <MdMenu
                        className={`my-auto me-1 h-full text-2xl ${user == null ? "text-light-green dark:text-dm-light-green" : "text-medium-green dark:text-dm-dark-green"}`}
                      />
                      <MdAccountCircle
                        className={`m-auto h-full text-3xl ${user == null ? "text-light-green dark:text-dm-light-green" : "text-medium-green dark:text-dm-dark-green"}`}
                      />
                    </MenuButton>
                  </div>
                  <MenuItems
                    transition
                    className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 transition focus:outline-none data-[closed]:scale-95 data-[closed]:transform data-[closed]:opacity-0 data-[enter]:duration-100 data-[leave]:duration-75 data-[enter]:ease-out data-[leave]:ease-in"
                  >
                    {user != null ? (
                      <>
                        <MenuItem>
                          {({ focus }) =>
                            user.profile == null ? (
                              <a
                                href="/creacion-perfil"
                                className={classNames(
                                  focus ? "bg-gray-100" : "",
                                  "text-md block px-4 py-2 text-gray-700",
                                )}
                              >
                                {" "}
                                Crear Perfil{" "}
                              </a>
                            ) : (
                              <a
                                href="/mi-perfil"
                                className={classNames(
                                  focus ? "bg-gray-100" : "",
                                  "text-md block px-4 py-2 text-gray-700",
                                )}
                              >
                                {" "}
                                Tu Perfil{" "}
                              </a>
                            )
                          }
                        </MenuItem>
                        <MenuItem>
                          {({ focus }) => (
                            <button
                              onClick={handleLogOut}
                              className={classNames(
                                focus ? "bg-gray-100" : "",
                                "text-md block w-full px-4 py-2 text-start text-gray-700",
                              )}
                            >
                              Cerrar sesión
                            </button>
                          )}
                        </MenuItem>
                      </>
                    ) : (
                      <>
                        <MenuItem>
                          {({ focus }) => (
                            <a
                              href="/iniciar-sesion"
                              className={classNames(
                                focus ? "bg-gray-100" : "",
                                "text-md block px-4 py-2 text-gray-700",
                              )}
                            >
                              Iniciar sesión
                            </a>
                          )}
                        </MenuItem>
                        <MenuItem>
                          {({ focus }) => (
                            <a
                              href="/registro"
                              className={classNames(
                                focus ? "bg-gray-100" : "",
                                "text-md block px-4 py-2 text-gray-700",
                              )}
                            >
                              Registrarme
                            </a>
                          )}
                        </MenuItem>
                      </>
                    )}
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
                    item.current
                      ? "bg-light-green text-white"
                      : "text-light-green hover:bg-light-green hover:text-white",
                    "block rounded-md px-3 py-2 text-base font-medium",
                  )}
                  aria-current={item.current ? "page" : undefined}
                >
                  {item.name}
                </DisclosureButton>
              ))}
              {user != null ? (
                <>
                  {user.profile == null ? (
                    <a
                      href="/creacion-perfil"
                      className="mt-2 block border-t border-light-green px-3 py-2 text-base font-medium text-light-green"
                    >
                      Crear Perfil
                    </a>
                  ) : (
                    <a
                      href="/mi-perfil"
                      className="mt-2 block border-t border-light-green px-3 py-2 text-base font-medium text-light-green"
                    >
                      Mi Perfil
                    </a>
                  )}
                  <button
                    onClick={handleLogOut}
                    className="block w-full px-3 py-2 text-start text-base font-medium text-light-green"
                  >
                    Cerrar sesión
                  </button>
                </>
              ) : (
                <>
                  <a
                    href="/iniciar-sesion"
                    className="mt-2 block border-t border-light-green px-3 py-2 text-base font-medium text-light-green"
                  >
                    Iniciar sesión
                  </a>
                  <a
                    href="/registro"
                    className="block px-3 py-2 text-base font-medium text-light-green"
                  >
                    Registrarme
                  </a>
                </>
              )}
            </div>
          </DisclosurePanel>
        </>
      )}
    </Disclosure>
  );
};
