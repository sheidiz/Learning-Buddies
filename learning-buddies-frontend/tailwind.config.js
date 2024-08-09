/** @type {import("tailwindcss").Config} */
export default {
	content: [
		"./index.html",
		"./src/**/*.{js,ts,jsx,tsx}",
	],
	darkMode: "media",
	theme: {
		extend: {
			boxShadow: {
				'inner-custom': 'inset 0 4px 4px 0 rgba(0,0,0,0.25)',
			},
			colors: {
				"light": "#EFF1ED",
				"dark": "#2A2A2A",
				"dark-grey": "rgba(42, 42, 42, 0.6)",
				"dark-green": "#373D20",
				"medium-green": "#717744",
				"light-green": "#BCBD8B",
				"brown": "#766153",
				"light-brown": "rgba(118, 87, 83, 0.5)",
				"dm-dark-green": "#30362F",
				"dm-medium-green": "#B3BD6C",
				"dm-light-green": "#929571",
				"dm-brown": "#5C4B3F",
				"dm-light-brown": "#9C9087",
			},
			fontFamily: {
				poppings: ["Poppins", "sans-serif", "ui-sans-serif"],
				raleway: ["Raleway", "sans-serif", "ui-sans-serif"]
			},
		},
	},
	plugins: [],
}
