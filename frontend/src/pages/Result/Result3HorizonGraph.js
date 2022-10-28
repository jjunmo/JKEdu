import { Bar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";
import { useState } from "react";

import styled from "styled-components";

const Graph = styled.div`
  width: 450px;
  height: 400px;

  @media screen and (max-width: 768px) {
    width: 90%;
    height: 300px;
    margin: 0 auto;
  }
`;

const Result3HorizonGraph = (prop) => {
  Chart.register(...registerables);

  // 나중에 결과를 받아와서 사용
  const [result, setResult] = useState(prop.result);

  const bardata = {
    labels: ["Speaking", "Reading", "Listening", "Grammar", "Writing"],
    datasets: [
      {
        label: "테스트 결과",
        backgroundColor: "#94DAFF",
        data: [100, 24, 44, 60, 83],
        borderColor: "white",
        borderWidth: 0,
        fill: false,
      },
    ],
  };

  return (
    <Graph>
      <Bar
        type="bar"
        data={bardata}
        options={{
          indexAxis: "y",
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            x: {
              suggestedMin: 1,
              suggestedMax: 100,
            },
          },
        }}
      />
    </Graph>
  );
};

export default Result3HorizonGraph;
